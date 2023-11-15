package se.iths.tictactoe.start;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.iths.tictactoe.controller.GameController;
import se.iths.tictactoe.enums.GameMode;

import java.io.IOException;
import java.util.Objects;

public class StartController {

    public void switchToPlayerView(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/game-view.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        GameController gc = fxmlLoader.getController();
        gc.prepareGame(GameMode.PLAYERVSPLAYER);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void switchToDifficultyView(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/difficulty-view.fxml")));
        Scene tableViewScene = new Scene(tableViewParent);


        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}
