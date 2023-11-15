module se.iths.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens se.iths.tictactoe to javafx.fxml;
    exports se.iths.tictactoe;
    exports se.iths.tictactoe.game;
    opens se.iths.tictactoe.game to javafx.fxml;
    exports se.iths.tictactoe.start;
    opens se.iths.tictactoe.start to javafx.fxml;
    exports se.iths.tictactoe.server;
    opens se.iths.tictactoe.server to javafx.fxml;
    exports se.iths.tictactoe.player;
    opens se.iths.tictactoe.player to javafx.fxml;
    exports se.iths.tictactoe.services;
    opens se.iths.tictactoe.services to javafx.fxml;
    exports se.iths.tictactoe.difficulty;
    opens se.iths.tictactoe.difficulty to javafx.fxml;
    exports se.iths.tictactoe.enums;
    opens se.iths.tictactoe.enums to javafx.fxml;
    exports se.iths.tictactoe.controller;
    opens se.iths.tictactoe.controller to javafx.fxml;
}