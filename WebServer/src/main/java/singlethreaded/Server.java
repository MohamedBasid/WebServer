package singlethreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run(){
        int port = 9090;
        try {
            ServerSocket socket = new ServerSocket(port);
            //socket.setSoTimeout(10000);
            while(true){
                System.out.println("Server listening to the port: "+ port);
                Socket acceptedConnection = socket.accept();
                System.out.println("Connection accepted from the client: " + acceptedConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
                toClient.println("Hello from the server!");
                //System.out.println("Client request: " + fromClient.readLine());
                toClient.close();
                fromClient.close();
                acceptedConnection.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }
}
