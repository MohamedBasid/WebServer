package withthreadpool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    Server(int noOfThreads){
        threadPool = Executors.newFixedThreadPool(noOfThreads);
    }

    private void handleClient(Socket socket) throws IOException {
        PrintWriter toClient = new PrintWriter(socket.getOutputStream());
        toClient.println("Hello from server! " + socket.getRemoteSocketAddress());
        toClient.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        int port = 9091;
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(10);
        System.out.println("Server listening to port: " + port);
        while(true) {
            Socket clientConnection = serverSocket.accept();
            System.out.println("Connection accepted from the client: " + clientConnection.toString());
            server.threadPool.execute(() -> {
                try {
                    server.handleClient(clientConnection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
