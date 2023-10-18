module se.iths.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.iths.tictactoe to javafx.fxml;
    exports se.iths.tictactoe;
}