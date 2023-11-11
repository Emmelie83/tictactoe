package se.iths.tictactoe.difficulty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.stage.Stage;
import se.iths.tictactoe.game.GameController;
import se.iths.tictactoe.enums.GameMode;

import java.io.IOException;

public class DifficultyController {

    @FXML
    private ScrollBar difficulty;


    public void switchToGameView(ActionEvent event) throws IOException
    {
        int value = (int) difficulty.getValue();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/game-view.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        GameController gc = fxmlLoader.getController();
        gc.setDifficulty(value);
        gc.prepareGame(GameMode.PLAYERVSCOMPUTER);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
