package se.iths.tictactoe.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpPublish {
    static HttpClient client = HttpClient.newHttpClient();
    static private final String url = "https://ntfy.sh/ej-tic-tac-toe";

    public static int sendGameState(String board, String command) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpPublish.url))
                .POST(HttpRequest.BodyPublishers.ofString(board + "," + command))
                .build();

        HttpResponse<Void> response =
                client.send(request, HttpResponse.BodyHandlers.discarding());

        System.out.println(response.statusCode());
        return response.statusCode();
    }
}
