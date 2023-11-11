package se.iths.tictactoe.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpPublish {
    static HttpClient client = HttpClient.newHttpClient();

    public static int sendGameState(String gameState) throws IOException, InterruptedException {
        //Reuse same client object during our programs lifetime

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe")) //Change java23iths to another topic name
                .POST(HttpRequest.BodyPublishers.ofString(gameState)) //Replace with your text
                .build();

        HttpResponse<Void> response =
                client.send(request, HttpResponse.BodyHandlers.discarding());

        //System.out.println(response.statusCode());
        return response.statusCode();
    }
}
