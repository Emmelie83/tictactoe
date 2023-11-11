package se.iths.tictactoe.server;

import javafx.application.Platform;
import se.iths.tictactoe.enums.Command;
import se.iths.tictactoe.game.GameController;
import se.iths.tictactoe.game.GameModel;
import se.iths.tictactoe.services.MappingService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class HttpConsume {

    static HttpClient client = HttpClient.newHttpClient();
    static MappingService mappingService = new MappingService();

    public static void startClient(GameController gameController, GameModel gameModel) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ntfy.sh/ej-tic-tac-toe/raw")) // raw = one line per message
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    reader.lines().forEach(line -> {

                        Platform.runLater(() -> {
                            String[] lines = line.split(",");
                            //ToDo: Check if Gameover
                            if (!Objects.equals(lines[0], gameController.getCurrentBoardString())) {

                                //Platform.runLater(() -> {
                                gameModel.gameModePlayerVsPlayerSetPlayer2();
                                // Update your model with the received message
                                updateModel(lines[0], lines[1], gameController, gameModel);

                                System.out.println(line);
                                //});
                            }
                            if (gameController.isGameOver()) {
                                return;
                            }
                        });
                    });
                });
    }

    private static void updateModel(String board, String command, GameController gameController, GameModel gameModel) {
        int[][] newBoard = mappingService.stringToBoard(board);
        gameModel.setNewBoard(newBoard);
        gameController.setButtonsByBoard(gameModel.getBoard());
        gameController.disableFlowPane(false);
        executeCommand(command, gameController);
        System.out.println(board);
    }

    private static void executeCommand(String command, GameController gameController) {
        Command eCommand = mappingService.mapStringToCommand(command);
        switch (eCommand) {
            case PLAYAGAIN -> {
                gameController.onPlayAgainReset();
            }
        }
    }
}
