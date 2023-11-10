import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ServerClientTest {
    HttpClient server = HttpClient.newHttpClient();
    HttpClient client = HttpClient.newHttpClient();
    @ParameterizedTest
    public int testThatServerCanSend(String gameState) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe")) //Change java23iths to another topic name
                .POST(HttpRequest.BodyPublishers.ofString(gameState)) //Replace with your text
                .build();

        HttpResponse<Void> response =
                server.send(request, HttpResponse.BodyHandlers.discarding());


        return response.statusCode();
    }

    @ParameterizedTest
    public String testThatClientCanReceive() throws IOException, InterruptedException {
        String result = "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe/raw")) // raw = one line per message
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                    reader.lines().forEach(line -> {

                        // process each line here
                        //Replace with updating model with incoming text message. Do this in Platform.runLater()
                        //Check for valid input. Strings may be empty.
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(line);
                    });
                });

    }



}
