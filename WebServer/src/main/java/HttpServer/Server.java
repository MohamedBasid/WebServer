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
        List<String> responses = new ArrayList<>();
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
            String toBePrinted = "";
            if (firstLine[1].equals("/") || firstLine[1].contains("/echo")) {
                responses.add("HTTP/1.1 200 OK\r\n\r\n");
                int startindex = firstLine[1].lastIndexOf("/echo/");
                toBePrinted = firstLine[1].substring(startindex + 6);
            }
            else {
                responses.add("HTTP/1.1 404 Not Found\r\n\r\n");
            }
            responses.add("Content-Type: text/plain");
            responses.add("Content-Length: " + toBePrinted.length());
            responses.add("");
            responses.add(toBePrinted);
        }
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        for(String response : responses) {
            writer.print(response + "\r\n");
        }
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
