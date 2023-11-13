package se.iths.tictactoe.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpPublish {
    static HttpClient client = HttpClient.newHttpClient();

    //static private String url = "https://ntfy.sh/ttt";
    //static private String url = "http://127.0.0.1:3000";
    static private final String url = "https://ntfy.sh/ej-tic-tac-toe";

    public static int sendGameState(String board, String command) throws IOException, InterruptedException {
        //Reuse same client object during our programs lifetime

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpPublish.url)) //Change java23iths to another topic name
                .POST(HttpRequest.BodyPublishers.ofString(board + "," + command)) //Replace with your text
                .build();

        HttpResponse<Void> response =
                client.send(request, HttpResponse.BodyHandlers.discarding());

        System.out.println(response.statusCode());
        return response.statusCode();
    }
}
