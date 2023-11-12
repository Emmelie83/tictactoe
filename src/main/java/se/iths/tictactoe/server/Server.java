package se.iths.tictactoe.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static String tag = "Server";
    private static String serverData = "serverData";

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(6000)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread.ofVirtual().start(() -> handleConnection(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Network error: " + e.getMessage());
        }
    }

    private static void handleConnection(Socket clientSocket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String fromClient = bufferedReader.readLine();
            System.out.println(tag + "handleConnection() "+ fromClient);
            Server.serverData = fromClient;


            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            bufferedWriter.write("Hello from server!\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}