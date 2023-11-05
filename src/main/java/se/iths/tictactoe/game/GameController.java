package se.iths.tictactoe.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.iths.tictactoe.services.MappingService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    public Label scoreText;
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

    private GameModel gameModel = new GameModel(3);
    private MappingService mappingService = new MappingService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new Button[][]{{button1, button2, button3}, {button4, button5, button6}, {button7, button8, button9}};
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                resetButton(button);
                setOnClick(button, i, j);
                button.setFocusTraversable(false);
            }
        }
    }

    private void setOnClick(Button button, int i, int j) {
        button.setOnMouseClicked(mouseEvent -> {
            if (playerSetButton(i, j)) {

                return;
            }
            computerSetButton();
        });
    }

    private boolean playerSetButton(int i, int j) {
        gameModel.playerMove(i, j);
        setButton(i, j);
        gameModel.changePlayer();
        return isGameOver();
    }

    //region computer
    private void computerSetButton() {
        flowPane.setDisable(true);
        int[] boardIndex = gameModel.computerBestMove();
        setButton(boardIndex[0], boardIndex[1]);
        gameModel.changePlayer();
        isGameOver();
        flowPane.setDisable(false);
    }
    //endregion


    private void setButton(int i, int j) {
        Player currentPlayer = gameModel.getCurrentPlayer();
        if (currentPlayer == Player.computer) {
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
        if (gameModel.isGameOver()) {
            setWinner();
            setScore();
            onGameOver();
            return true;
        }
        return false;
    }

    public void setWinner() {
        Player winner = gameModel.getWinner();
        if (winner == Player.human) {
            winnerText.setText("X won!");
        }
        if (winner == Player.computer) {
            winnerText.setText("O won!");
        }
        if (winner == Player.none) {
            winnerText.setText("Draw!");
        }
    }

    public void setScore() {
        scoreText.setText(gameModel.getPlayerScore() + " - " + gameModel.getComputerScore());
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
        button.setText(mappingService.getValueFromPlayerEnum(Player.none));
    }

    public void switchToScoreView() throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/score-view.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);


        Stage window = (Stage) tableViewParent.getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}