package loadbalancer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
   private Socket clientSocket;

   RequestHandler(Socket clientSocket) {
       this.clientSocket = clientSocket;
   }
    @Override
    public void run() {
        try {
            InputStream fromClient = clientSocket.getInputStream();
            OutputStream toClient = clientSocket.getOutputStream();

            int serverPort = Apps.getHost();
            Socket serverSocket = new Socket("localhost", serverPort);
            System.out.println("Server connected is: " + serverPort);
            InputStream fromApp = serverSocket.getInputStream();
            OutputStream toApp = serverSocket.getOutputStream();
            toApp.write(fromClient.readAllBytes());
            toClient.write(fromApp.readAllBytes());
            fromClient.close();
            toClient.close();
            fromApp.close();
            toApp.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
