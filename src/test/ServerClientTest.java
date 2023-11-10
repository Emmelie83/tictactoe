import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ServerClientTest {
    HttpClient server = HttpClient.newHttpClient();
    HttpClient client = HttpClient.newHttpClient();

    public int testThatServerCanSend(String gameState) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe")) //Change java23iths to another topic name
                .POST(HttpRequest.BodyPublishers.ofString(gameState)) //Replace with your text
                .build();

        HttpResponse<Void> response =
                server.send(request, HttpResponse.BodyHandlers.discarding());


        return response.statusCode();
    }

@Test
    public void testThatClientCanReceive() throws ExecutionException, InterruptedException, TimeoutException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe/raw"))
                .build();

        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);



        /* return client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                .thenApply(HttpResponse::body)
                .thenApply(lines -> {
                    StringBuilder resultBuilder = new StringBuilder();

                    lines.forEach(line -> {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(line);

                        // Process each line and append it to the result
                        resultBuilder.append(line).append("\n");
                    });

                    return resultBuilder.toString();
                })
                .exceptionally(throwable -> {
                    // Handle exceptions here
                    throwable.printStackTrace();
                    return ""; // Return an empty string or another default value
                }); */

    }




    @Test
    public void writeAndReceive() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        testThatServerCanSend("Hej Jan!\n");
        testThatClientCanReceive();

        //CompletableFuture<String> result = testThatClientCanReceive();

        // Assert that the CompletableFuture completes successfully and the result is as expected
        //assertThat(result.join()).isEqualTo("Hej Jan!\n");
    }

}
