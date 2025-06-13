import java.util.*;
import java.util.concurrent.*;
import java.text.*;

class SemaphoreDB {
    static Integer[] buffer;
    static int in = 0;
    static int out = 0;

    static Semaphore mutex;
    static Semaphore empty;
    static Semaphore full;

    static Random r = new Random();
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("N = ");
        int N = scanner.nextInt();
        SemaphoreDB.buffer = new Integer[N];

        System.out.print("K =  ");
        int k = scanner.nextInt();

        System.out.print("H = ");
        int h = scanner.nextInt();

        // Khởi tạo semaphore
        SemaphoreDB.mutex = new Semaphore(0);
        SemaphoreDB.empty = new Semaphore(N);
        SemaphoreDB.full = new Semaphore(0);

        // Khởi tạo luồng producer
        for (int i = 1; i <= k; i++) {
            int id = i;
            new Thread(() -> producer(id, N)).start();
        }

        // Khởi tạo luồng consumer
        for (int i = 1; i <= h; i++) {
            int id = i;
            new Thread(() -> consumer(id, N)).start();
        }
    }

    // Luồng sinh dữ liệu
    static void producer(int id, int N) {
        while (true) {
            try {
                Thread.sleep(SemaphoreDB.r.nextInt(1000));
                int value = SemaphoreDB.r.nextInt(1000); // Sinh số ngẫu nhiên

                SemaphoreDB.empty.acquire();     // chờ nếu buffer đầy
                SemaphoreDB.mutex.acquire();     // vào vùng độc quyền

                SemaphoreDB.buffer[SemaphoreDB.in] = value;
                SemaphoreDB.in = (SemaphoreDB.in + 1) % N;

                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                System.out.println("P" + id + ": " + value + " - " + time);

                SemaphoreDB.mutex.release();     // cho luồng khác vào
                SemaphoreDB.full.release();      // báo có dữ liệu mới

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Luồng xử lý dữ liệu
    static void consumer(int id, int N) {
        while (true) {
            try {
                Thread.sleep(SemaphoreDB.r.nextInt(1500));

                SemaphoreDB.full.acquire();     // chờ nếu buffer rỗng
                SemaphoreDB.mutex.acquire();    // vào vùng găng

                Integer value = SemaphoreDB.buffer[SemaphoreDB.out];
                if (value != null) {
                    SemaphoreDB.buffer[SemaphoreDB.out] = null; // xóa sau khi đọc
                    SemaphoreDB.out = (SemaphoreDB.out + 1) % N;

                    boolean isEven = (value % 2 == 0);
                    String result = isEven ? "Chẵn" : "Lẻ";

                    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    System.out.println("C" + id + ": " + value + " - " + result + " - " + time);

                }

                SemaphoreDB.mutex.release();    // thoát vùng găng
                SemaphoreDB.empty.release();    // báo là có thêm ô trống
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
