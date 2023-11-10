import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerClient2Test {
    HttpClient client = HttpClient.newHttpClient();

    public void getMessage() {
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
        //Not necessary in JavaFx Application
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}