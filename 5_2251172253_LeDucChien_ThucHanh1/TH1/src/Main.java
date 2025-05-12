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
        int n = 200;
        int k = 4;
        mangA mang = new mangA(n);
        int phanluong = n/k;
        luongA t1 = new luongA(mang, 0, phanluong, "T1");
        luongA t2 = new luongA(mang,phanluong, 2 * phanluong, "T2");
        luongA t3 = new luongA(mang, 2 * phanluong, 3 * phanluong, "T3");
        luongA t4 = new luongA(mang, 3 * phanluong, n, "T4");

        // Khởi chạy từng luồng
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Dùng join() để đợi tất cả luồng kết thúc
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Tổng số chính phương tìm được: " + mang.d);
    }
}