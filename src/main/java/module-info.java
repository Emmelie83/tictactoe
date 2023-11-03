module se.iths.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens se.iths.tictactoe to javafx.fxml;
    exports se.iths.tictactoe;
}