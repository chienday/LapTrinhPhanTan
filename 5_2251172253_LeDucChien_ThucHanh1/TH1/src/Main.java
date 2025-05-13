import java.util.*;
import java.time.*;



// mang luu dung chung
class mangA{
    int [] A;
    int d = 0;
    public mangA(int n){
        A = new int[n];
        Random r = new Random();
        for(int i = 0; i<n; i++){
            A[i] = r.nextInt(1000);
        }
        A[n-1]=4;
    }
    public static boolean ktraCP(int n){
        int x = (int)Math.sqrt(n);
        return x*x == n;
    }
    public void dem(){
        d++;
    }
}
class luongA extends Thread{
    mangA mang;
    int start,end;
    String name;

    public luongA(mangA mang ,int start, int end, String name){
        this.mang = mang;
        this.start = start;
        this.end = end;
        this.name = name;

    }
    @Override
    public void run(){
        for(int i = start; i<end; i++){
            if(mangA.ktraCP(mang.A[i])){
                mang.dem();
                System.out.println(name + ": "+mang.A[i]+" :"+LocalTime.now());
            }
        }
    }
}





public class Main {
    public static void main(String[] args) throws InterruptedException{
       Scanner sc = new Scanner(System.in);
        int n, k;

        // Nhập và kiểm tra điều kiện n > 100 và k > 1
        do {
            System.out.print("Nhập số phần tử của mảng (n > 100): ");
            n = sc.nextInt();
        } while (n <= 100);

        do {
            System.out.print("Nhập số lượng luồng (k > 1): ");
            k = sc.nextInt();
        } while (k <= 1);
        mangA mang = new mangA(n);
        int phanluong = n/k;
        luongA[] threads = new luongA[k];
        for (int i = 0; i < k; i++) {
            int start = i * phanluong;
            int end = (i == k - 1) ? n : (i + 1) * phanluong; // luồng cuối lấy hết phần còn lại
            threads[i] = new luongA(mang, start, end, "T" + (i + 1));
            threads[i].start();
        }
        for (int i = 0; i < k; i++) {
            threads[i].join();
        }


        System.out.println("Tổng số chính phương tìm được: " + mang.d);
    }
}
