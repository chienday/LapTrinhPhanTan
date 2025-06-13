package camon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Even extends Remote {
    String[] checkEvenOdd(int[] numbers) throws RemoteException;
    int phandu(int a, int b) throws RemoteException;
}
