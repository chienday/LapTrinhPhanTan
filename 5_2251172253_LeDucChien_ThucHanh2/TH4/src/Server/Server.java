package Server;

import camon.Even;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Main Server
public class Server {
    public static void main(String[] args) {
        try {
            // Tạo đối tượng từ xa
            Even obj = new EvenServer();

            // Tạo registry RMI
            Registry registry = LocateRegistry.createRegistry(1099);

            // Đăng ký đối tượng từ xa với tên "EvenOddService"
            registry.rebind("EvenOddService", obj);

            System.out.println("Server sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.toString());
            e.printStackTrace();
        }
    }
}
