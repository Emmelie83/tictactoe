package se.iths.tictactoe.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.iths.tictactoe.enums.GameMode;
import se.iths.tictactoe.enums.Player;
import se.iths.tictactoe.enums.Command;
import se.iths.tictactoe.server.HttpConsume;
import se.iths.tictactoe.server.HttpPublish;
import se.iths.tictactoe.services.MappingService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    public Button playAgainButton;
    private String tag = "GameController";

    @FXML
    private Label scoreText;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Text winnerText;

    @FXML
    private FlowPane flowPane;

    @FXML
    public Button closeButton;

    private Button[][] buttons;
    private GameModel gameModel = new GameModel();
    private MappingService mappingService = new MappingService();
    private int difficulty;

    public String getCurrentBoardString() { return gameModel.currentBoardString; };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new Button[][]{{button1, button2, button3}, {button4, button5, button6}, {button7, button8, button9}};
    }

    public void initializeButtons() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                resetButton(button);
                setOnClick(button, i, j, gameModel.getGameMode());
                button.setFocusTraversable(false);
            }
        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void prepareGame(GameMode gameMode) {
        gameModel.setGameMode(gameMode);
        initializeButtons();
        if (gameMode == GameMode.PLAYERVSPLAYER) {
            HttpConsume.startClient(this, this.gameModel);
        }
    }


    private void setOnClick(Button button, int i, int j, GameMode gameMode) {
        if (gameMode == GameMode.PLAYERVSCOMPUTER) {
            button.setOnMouseClicked(mouseEvent -> {
                if (vsComputerPlayersTurn(i, j)) return;
                vsComputerComputersTurn();
            });
        }
        if (gameMode == GameMode.PLAYERVSPLAYER) {
            button.setOnMouseClicked(mouseEvent -> {
                try {
                    vsPlayerPlayersTurn(i, j);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    private boolean vsComputerPlayersTurn(int i, int j) {
        int[][] board = gameModel.getBoard();
        gameModel.isBoardSet(board, i, j, Player.PLAYER1);
        setButton(i, j, gameModel.getCurrentPlayer());
        gameModel.changePlayer();
        return isGameOver();
    }

    private void vsPlayerPlayersTurn(int i, int j) throws IOException, InterruptedException {
        gameModel.gameModePlayerVsPlayerSetPlayer1();
        int[][] board = gameModel.getBoard();
        gameModel.isBoardSet(board, i, j, gameModel.getCurrentPlayer());
        setButton(i, j, gameModel.getCurrentPlayer());
        String stringBoard = mappingService.boardToString(board);
        gameModel.currentBoardString = stringBoard;
        HttpPublish.sendGameState(stringBoard, Command.NONE.toString());
        //flowPane.setDisable(true);
        disableFlowPane(true);
    }

    private void vsComputerComputersTurn() {
        disableFlowPane(true);
        int[] boardIndex = gameModel.computerMove(difficulty);
        int[][] board = gameModel.getBoard();
        gameModel.isBoardSet(board, boardIndex[0], boardIndex[1], Player.COMPUTER);
        setButton(boardIndex[0], boardIndex[1], gameModel.getCurrentPlayer());
        gameModel.changePlayer();
        isGameOver();
        disableFlowPane(false);
    }

    public void setButtonsByBoard(int[][] board) {
        try {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    int ordinal = board[i][j];
                    Player player = mappingService.mapOrdinalToPlayerEnum(ordinal);
                    setButtonByBoard(i, j, player);
                }
            }
        } catch (Exception e) {
            System.out.println(tag + " setButtons() " + e.toString());
        }
    }
        private void setButtonByBoard(int i, int j, Player player) {
            if (player ==  Player.NONE) {
                buttons[i][j].setText(mappingService.getValueFromPlayerEnum(player));
                buttons[i][j].setDisable(false);
                return;
            }

            buttons[i][j].setText(mappingService.getValueFromPlayerEnum(player));
            buttons[i][j].setTextFill(mappingService.getColorFromPlayerEnum(player));
            buttons[i][j].setDisable(true);
        }

    private void setButton(int i, int j, Player currentPlayer) {
        buttons[i][j].setText(mappingService.getValueFromPlayerEnum(currentPlayer));
        buttons[i][j].setTextFill(mappingService.getColorFromPlayerEnum(currentPlayer));
        buttons[i][j].setDisable(true);

        //Player currentPlayer = gameModel.getCurrentPlayer();
//        if (currentPlayer == Player.COMPUTER || currentPlayer == Player.PLAYER2) {
//            buttons[i][j].setText(mappingService.getValueFromPlayerEnum(currentPlayer));
//            buttons[i][j].setTextFill(Color.GREEN);
//        } else {
//            buttons[i][j].setText(mappingService.getValueFromPlayerEnum(currentPlayer));
//            buttons[i][j].setTextFill(Color.RED);
//        }
//        buttons[i][j].setDisable(true);
    }


    public void onGameOver() {
        if (gameModel.getCurrentPlayer() == Player.PLAYER1 || gameModel.getCurrentPlayer() == Player.COMPUTER) {
            showPlayAgainButton(true);
        }
        disableAllButtons();
    }

    private void disableAllButtons() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                button.setDisable(true);
            }
        }
    }

    public boolean isGameOver() {
        if (gameModel.isGameOver(gameModel.getBoard())) {
            setWinner();
            setScore();
            onGameOver();
            return true;
        }
        return false;
    }

    public void setWinner() {
        Player winner = gameModel.getWinner();

        if (winner == Player.PLAYER1) winnerText.setText(mappingService.getValueFromPlayerEnum(Player.PLAYER1) + " won!");
        if (winner == Player.COMPUTER) winnerText.setText(mappingService.getValueFromPlayerEnum(Player.COMPUTER) + " won!");
        if (winner == Player.PLAYER2) winnerText.setText(mappingService.getValueFromPlayerEnum(Player.PLAYER2) + " won!");
        if (winner == Player.NONE) winnerText.setText("Draw!");
    }

    public void setScore() {
        scoreText.setText(gameModel.getPlayerXScore() + " - " + gameModel.getPlayerOScore());
    }

    @FXML
    void playAgain(ActionEvent event) throws IOException, InterruptedException {
        gameModel.resetBoard();
        resetButtons(gameModel.getBoard());
        onPlayAgainReset();
        gameModel.resetPlayer();
        showPlayAgainButton(false);

        if(gameModel.getGameMode() == GameMode.PLAYERVSCOMPUTER) return;
        gameModel.resetCurrentBoardString();
        gameModel.currentBoardString = mappingService.boardToString(gameModel.getBoard());
        HttpPublish.sendGameState(gameModel.currentBoardString, Command.PLAYAGAIN.toString());
        disableFlowPane(true);
    }

    public void onPlayAgainReset() {
        winnerText.setText("Tic-Tac-Toe");
    }

    public void showPlayAgainButton(boolean showButton) {
        playAgainButton.setVisible(showButton);
    }


    private void resetButtons(int[][] board) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                resetButton(button);
            }
        }
    }


    public void resetButton(Button button){
        button.setDisable(false);
        button.setText(mappingService.getValueFromPlayerEnum(Player.NONE));
    }

    public void disableFlowPane(boolean disable) {
       flowPane.setDisable(disable);
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) throws IOException, InterruptedException {
        HttpPublish.sendGameState("",Command.EXITGAME.toString());
        exitGame();
    }

    public void exitGame() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}