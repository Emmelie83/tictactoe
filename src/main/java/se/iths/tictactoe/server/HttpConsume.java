package se.iths.tictactoe.server;

import javafx.application.Platform;
import se.iths.tictactoe.enums.Command;
import se.iths.tictactoe.controller.GameController;
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

    private static final String tag = "HttpConsume";
    static private final String url = "https://ntfy.sh/ej-tic-tac-toe/raw";
    static HttpClient client = HttpClient.newHttpClient();
    static MappingService mappingService = new MappingService();

    public static void startClient(GameController gameController, GameModel gameModel) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpConsume.url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    reader.lines().forEach(line -> {

                        Platform.runLater(() -> {
                            try {
                                System.out.println("line:" + line);
                                if(line == null || Objects.equals(line, "") ) return;
                                String[] lines = line.split(",");
                                String board = lines[0];
                                String command = lines[1];

                                boolean isYourTurn = isYourTurn(board, gameController);
                                updateBoard(isYourTurn, board, gameController, gameModel);
                                executeCommand(isYourTurn, command, gameController);
                                gameController.isGameOver();
                            }catch (Exception e) {
                                System.out.println(HttpConsume.tag + " " + e);
                            }

                        });
                    });
                });
    }

    private static void updateBoard(boolean isYourTurn,  String board, GameController gameController, GameModel gameModel) {
        if(!isYourTurn) return;

        gameModel.vsPlayerSetPlayer2();

        int[][] newBoard = mappingService.stringToBoard(board);
        gameModel.setNewBoard(newBoard);

        gameController.setButtonsByBoard(gameModel.getBoard());

        gameController.disableFlowPane(false);
        System.out.println(board);
    }

    private static void executeCommand(boolean isYourTurn, String command, GameController gameController) {
        if(!isYourTurn) return;
        try {
            Command eCommand = mappingService.mapStringToCommand(command);
            if(eCommand == null) throw new Exception(HttpConsume.tag + "Exception executeCommand() command: "+command+ " e: eCommand == null");
            switch (eCommand) {
                case PLAYAGAIN -> { gameController.onPlayAgainReset(); gameController.showPlayAgainButton(false); }
                case EXITGAME -> gameController.exitGame();
            }
        } catch (Exception e) {
            System.out.println(HttpConsume.tag + "executeCommand() " +e);
        }
    }

    private static boolean isYourTurn(String board, GameController gameController) {
        return !Objects.equals(board, gameController.getCurrentBoardString());
    }
}
