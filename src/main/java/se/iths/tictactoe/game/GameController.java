package se.iths.tictactoe.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {

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

    private int playerOneScore = 0;

    private int playerTwoScore = 0;

    private Button[][] buttons;

    private GameModel gameModel = new GameModel(3);

    private Player currentPlayer = Player.human;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new Button[][]{{button1, button2, button3}, {button4, button5, button6}, {button7, button8, button9}};
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                resetButton(button);
                playerMove(button, i, j);
                button.setFocusTraversable(false);
            }
        }
    }

    @FXML
    void restartGame(ActionEvent event) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                resetButton(button);
            }
        }
        winnerText.setText("Tic Tac Toe");
    }

    public void resetButton(Button button){
        button.setDisable(false);
        button.setText(" ");
    }

    private void playerMove(Button button, int i, int j) {
        button.setOnMouseClicked(mouseEvent -> {
            setButton(i, j, currentPlayer);
            checkIfGameIsOver();
            gameModel.setBoard(i, j, BoardValue.player);
            changePlayer();
            computerMove();
        });
    }

    private void computerMove() {
        flowPane.setDisable(true);
        int[] boardIndex = computerMoveSetBoard();
        flowPane.setDisable(false);
        setButton(boardIndex[0], boardIndex[1], currentPlayer);
        changePlayer();
    }

    private int[] computerMoveSetBoard() {
        Random random = new Random();
        int i = 0;
        int j = 0;
        boolean isFree = true;
        do {
            i = random.nextInt(3);
            System.out.println(i);
            j = random.nextInt(3);
            System.out.println(j);
            isFree = gameModel.setBoard(i, j, BoardValue.computer);
        } while (isFree == false);
        return new int[] {i ,j };
    }

    private void setButton(int i, int j, Player currentPlayer) {
        if (currentPlayer == Player.computer) {
            buttons[i][j].setText("O");
            buttons[i][j].setTextFill(Color.GREEN);
        } else {
            buttons[i][j].setText("X");
            buttons[i][j].setTextFill(Color.RED);
        }
        buttons[i][j].setDisable(true);
    }

    private void changePlayer() {
        if (currentPlayer == Player.human) currentPlayer = Player.computer;
        else currentPlayer = Player.human;
    }


    public void onGameOver() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                button.setDisable(true);
            }
        }
    }

    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if (line.equals("XXX")) {
                winnerText.setText("X won!");
                playerOneScore++;
                onGameOver();
            }

            else if (line.equals("OOO")) {
                winnerText.setText("O won!");
                playerTwoScore++;
                onGameOver();
            }
        }
        if (counter >= 9) {
            winnerText.setText("Tie!");
        }
    }
}