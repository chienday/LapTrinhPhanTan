package Server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(13000);// TCP
        System.out.println("Server.Server đang lắng nghe...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client đã kết nối: " + clientSocket);

            new Thread(() -> handleClient(clientSocket)).start();
        }
    }

    static void handleClient(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String data = in.readLine(); // Nhận mảng từ client (dạng chuỗi)
            System.out.println("Nhận từ client: " + data);

            // Phân tích mảng và lọc số chẵn
            String[] parts = data.trim().split("\\s+");
            List<Integer> evenNumbers = new ArrayList<>();
            for (String s : parts) {
                s = s.replaceAll("[^\\d-]", "");
                int num = Integer.parseInt(s);
                if (num % 2 == 0) {
                    evenNumbers.add(num);
                }
            }
            System.out.println("Tra ve cho client"+evenNumbers);

            // Gửi lại các số chẵn
            out.println("Mang:" + data + " So chan: " + evenNumbers.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
