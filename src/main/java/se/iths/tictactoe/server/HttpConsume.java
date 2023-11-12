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

    private static final String tag = "HttpConsume";

    private static final String url = "https://ntfy.sh/ej-tic-tac-toe/raw";
    private static final HttpClient client = HttpClient.newHttpClient();
    static MappingService mappingService = new MappingService();

    public static void startClient(GameController gameController, GameModel gameModel) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpConsume.url))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    reader.lines().forEach(line -> Platform.runLater(() -> {
                        try {
                            System.out.println("line:" + line);
                            //set Player2 as current player
                            if(line == null || Objects.equals(line, "") ) return;
                            //get string contents
                            String[] lines = line.split(",");
                            String board = lines[0];
                            String command = lines[1];

                            boolean isYourTurn = isYourTurn(board, gameController);
                            // Update your model with the received message
                            updateBoard(isYourTurn, board, gameController, gameModel);
                            executeCommand(isYourTurn, command, gameController);

                           if (gameController.isGameOver()) {
                                String wer = "sd";
                            }
                        }catch (Exception e) {
                            System.out.println(HttpConsume.tag + " " + e);
                        }

                    }));
                });
    }

    private static void updateBoard(boolean isYourTurn,  String board, GameController gameController, GameModel gameModel) {
        //return if board is the same (means: others player turn)
        if(!isYourTurn) return;

        gameModel.vsPlayerSetPlayer2();

        //get new board
        int[][] newBoard = mappingService.stringToBoard(board);
        gameModel.setNewBoard(newBoard);

        //update GUI by new board-array
        gameController.setButtonsByBoard(gameModel.getBoard());

        //active GUI so that player can play
        gameController.disableFlowPane(false);
        System.out.println(board);
    }

    private static void executeCommand(boolean isYourTurn, String command, GameController gameController) {
        if(!isYourTurn) return;
        try {
            Command eCommand = mappingService.mapStringToCommand(command);
            if(eCommand == null) throw new Exception(HttpConsume.tag + "Exception executeCommand() command: "+command+ " e: eCommand == null");
            switch (eCommand) {
                case PLAYAGAIN -> gameController.onPlayAgainReset();
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
