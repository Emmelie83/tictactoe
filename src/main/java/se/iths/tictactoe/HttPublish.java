package se.iths.tictactoe;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttPublish {


    public static void main(String[] args) throws IOException, InterruptedException {
        //Reuse same client object during our programs lifetime
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe")) //Change java23iths to another topic name
                .POST(HttpRequest.BodyPublishers.ofString("Hej Emmelie!")) //Replace with your text
                .build();

        HttpResponse<Void> response =
                client.send(request, HttpResponse.BodyHandlers.discarding());

        System.out.println(response.body());
    }

}
