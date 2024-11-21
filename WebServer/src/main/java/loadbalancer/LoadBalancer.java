package loadbalancer;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadBalancer {

    private ExecutorService executorService;

    LoadBalancer(int noOfThreads) {
        this.executorService = Executors.newFixedThreadPool(noOfThreads);
    }
    public static void main(String[] args) throws IOException {
        int port = 8081;
        ServerSocket serverSocket = new ServerSocket(port);
        LoadBalancer loadBalancer = new LoadBalancer(10);
        System.out.println("Loadbalancer listening to port: " + port);
        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("TCP connection established: " + clientSocket.toString());
            RequestHandler requestHandler = new RequestHandler(clientSocket);
            Thread requestHandlingThread = new Thread(requestHandler);
            requestHandlingThread.start();
        }
    }
}
