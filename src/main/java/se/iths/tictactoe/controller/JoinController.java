package se.iths.tictactoe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JoinController {
    public void switchToGameView2(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/game-view.fxml")));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
