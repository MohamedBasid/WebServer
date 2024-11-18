package MultiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Runnable getRunnable(){
        //return null;
        return () -> {
            int port = 8080;
            try {
                InetAddress address = InetAddress.getByName("localhost");
                Socket socket = new Socket(address, port);
                PrintWriter toSocket = new PrintWriter(socket.getOutputStream());
                BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                toSocket.println("Hello from the client: " + socket.getLocalSocketAddress());
                System.out.println("Server response: " + fromSocket.readLine());
                toSocket.close();
                fromSocket.close();
                socket.close();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        };
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        for(int i = 0; i < 20; i++) {
            Thread thread = new Thread(client.getRunnable());
            thread.start();
        }
    }
}
