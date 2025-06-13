package Server;
import camon.Even;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Arrays;

public class EvenServer extends UnicastRemoteObject implements Even {

    public EvenServer() throws RemoteException {
        super();
    }

    @Override
    public String[] checkEvenOdd(int[] numbers) throws RemoteException {
        System.out.println("Server nhận mảng: " + Arrays.toString(numbers));

        String[] results = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] % 2 == 0) {
                results[i] = numbers[i] + " là số chẵn.";
            } else {
                results[i] = numbers[i] + " là số lẻ.";
            }
        }

        System.out.println("Kết quả xử lý:");
        for (String res : results) {
            System.out.println(" - " + res);
        }

        return results;
    }
    @Override
    public int phandu(int a, int b) throws RemoteException {
            System.out.println("Nhận từ client " + a + " % " + b);
            if (b == 0) {
                System.out.println("Lỗi: chia cho 0");
                throw new RemoteException("Không thể chia cho 0");
            }

            int result = a % b;
            System.out.println(" Kết quả phần dư: " + result);
            return result;


    }
}
