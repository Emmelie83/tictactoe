package se.iths.tictactoe.server;

import java.io.*;
import java.net.Socket;

public class Client {

    //private Socket socket;

    private DataInputStream fromServer;
    private DataOutputStream toServer;


    public Client() throws IOException {
        this.init();
    }

    private void init() throws IOException {
        //socket = new Socket("127.0.0.1", 6000);

//        fromServer = new DataInputStream(socket.getInputStream());
//        toServer = new DataOutputStream(socket.getOutputStream());
    }

    public void toServer(String message) throws IOException {

        //Ansluta sig till en server, behöver veta
        //Serverns ip address : 127.0.0.1, 192.168.1.179, localhost
        //Serverns port nummer 16-bit: 6000

        try (Socket socket = new Socket("127.0.0.1", 6000)) {
            System.out.println("toServer() "+ message);
            //BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(message);
            bufferedWriter.flush();

//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            String text = bufferedReader.readLine();
            //System.out.println(text);
        }catch (IOException e){

        }
    }


    public String fromServer() {

        //Ansluta sig till en server, behöver veta
        //Serverns ip address : 127.0.0.1, 192.168.1.179, localhost
        //Serverns port nummer 16-bit: 6000

        try (Socket socket = new Socket("127.0.0.1", 6000)) {
//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            bufferedWriter.write("Hello from client!\n");
//            bufferedWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String text = bufferedReader.readLine();
            System.out.println(text);
            return text;

        }catch (IOException e){
            return null;
        }
    }
}