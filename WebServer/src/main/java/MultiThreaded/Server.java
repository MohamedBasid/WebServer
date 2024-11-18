package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        return (socket) -> {
            try {
                PrintWriter toClient = new PrintWriter(socket.getOutputStream());
                toClient.println("Hello from server: " + socket.getRemoteSocketAddress());
                toClient.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server();
        while(true) {
            System.out.println("Server listening to the port: " + port);
            Socket acceptedConnection = serverSocket.accept();
            Thread thread = new Thread(() -> server.getConsumer().accept(acceptedConnection));
            thread.start();
        }
    }
}
