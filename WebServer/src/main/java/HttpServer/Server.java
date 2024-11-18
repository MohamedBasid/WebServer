package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static void handleRequest(Socket socket) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\n\r\n";
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        List<String> request = new ArrayList<>();
        while(true) {
            String line = reader.readLine();
            if("".equals(line)) {
                break;
            }
            System.out.println("Header: " + line);
            request.add(line);
        }
        if(request.get(0).contains("GET")) {
            String[] firstLine = request.get(0).split(" ");
            if (firstLine[1].equals("/")) {
                response = "HTTP/1.1 200 OK\r\n\r\n";
            }
        }
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.print(response);
        writer.flush();
        reader.close();
        writer.close();
    }
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port: " + port);
        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("connection created");
            handleRequest(clientSocket);
            clientSocket.close();
        }
    }
}
