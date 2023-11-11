package se.iths.tictactoe.server;

import javafx.application.Platform;
import se.iths.tictactoe.game.GameController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpConsume {

    static HttpClient client = HttpClient.newHttpClient();

    public static void startClient(GameController gameController) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe/raw")) // raw = one line per message
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    reader.lines().forEach(line -> {
                        Platform.runLater(() -> {
                            // Update your model with the received message
                            updateModel(line);

                            System.out.println(line);
                        });
                        // process each line here
                        //Replace with updating model with incoming text message. Do this in Platform.runLater()
                        //Check for valid input. Strings may be empty.
                    });
                });
    }

    private static void updateModel(String message) {
        System.out.println(message);
    }
}
