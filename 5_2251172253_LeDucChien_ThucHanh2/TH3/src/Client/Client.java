package Client;
import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("127.0.0.1", 13000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            // Nhập số phần tử N từ bàn phím
            System.out.print("N = ");
            int N = scanner.nextInt();

            // Sinh mảng A gồm N phần tử ngẫu nhiên
            Random rand = new Random();
            int[] A = new int[N];
            for (int i = 0; i < N; i++) {
                A[i] = rand.nextInt(1000); // Số ngẫu nhiên từ 0–999
            }

            // Chuyển mảng thành chuỗi cách nhau bằng dấu trắng
            StringBuilder sb = new StringBuilder();
            for (int x : A) {
                sb.append(x).append(" ");
            }
            System.out.print("Mảng A: ");
            for (int x : A) {
                System.out.print(x + " ");
            }
            System.out.println();

            // Gửi mảng cho server
            out.println(sb.toString().trim());

            // Nhận kết quả từ server
            String result = in.readLine();
            System.out.println("Kết quả từ server: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
