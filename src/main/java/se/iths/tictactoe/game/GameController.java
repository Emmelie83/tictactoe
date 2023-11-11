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
import se.iths.tictactoe.server.HttpConsume;
import se.iths.tictactoe.server.HttpPublish;
import se.iths.tictactoe.services.MappingService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
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
            HttpConsume.startClient(this);
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    private boolean vsComputerPlayersTurn(int i, int j) {
        int[][] board = gameModel.getBoard();
        gameModel.isBoardSet(board, i, j, Player.PLAYER1);
        setButton(i, j);
        gameModel.changePlayer();
        return isGameOver();
    }

    private void vsPlayerPlayersTurn(int i, int j) throws IOException, InterruptedException {
        int[][] board = gameModel.getBoard();
        gameModel.isBoardSet(board, i, j, gameModel.getCurrentPlayer());
        setButton(i, j);
        String stringBoard = mappingService.boardToString(board);
        HttpPublish.sendGameState(stringBoard);
    }

    private void vsComputerComputersTurn() {
        flowPane.setDisable(true);
        int[] boardIndex = gameModel.computerMove(difficulty);
        int[][] board = gameModel.getBoard();
        gameModel.isBoardSet(board, boardIndex[0], boardIndex[1], Player.COMPUTER);
        setButton(boardIndex[0], boardIndex[1]);
        gameModel.changePlayer();
        isGameOver();
        flowPane.setDisable(false);
    }


    private void setButton(int i, int j) {
        Player currentPlayer = gameModel.getCurrentPlayer();
        if (currentPlayer == Player.COMPUTER || currentPlayer == Player.PLAYER2) {
            buttons[i][j].setText(mappingService.getValueFromPlayerEnum(currentPlayer));
            buttons[i][j].setTextFill(Color.GREEN);

        } else {
            buttons[i][j].setText(mappingService.getValueFromPlayerEnum(currentPlayer));
            buttons[i][j].setTextFill(Color.RED);
        }
        buttons[i][j].setDisable(true);
    }


    public void onGameOver() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                button.setDisable(true);
            }
        }
    }

    private boolean isGameOver() {
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
        if (winner == Player.PLAYER1) winnerText.setText("X won!");
        if (winner == Player.COMPUTER) winnerText.setText("O won!");
        if (winner == Player.PLAYER2) winnerText.setText("O won!");
        if (winner == Player.NONE) winnerText.setText("Draw!");
    }

    public void setScore() {
        scoreText.setText(gameModel.getPlayerXScore() + " - " + gameModel.getPlayerOScore());
    }

    @FXML
    void resetGame(ActionEvent event) {
        gameModel.resetBoard();
        resetButtons(gameModel.getBoard());
        winnerText.setText("Tic-Tac-Toe");
        gameModel.resetPlayer();
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


    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}