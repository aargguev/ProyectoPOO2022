module ec.edu.espol.proyectorover {
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.espol.proyectorover to javafx.fxml;
    exports ec.edu.espol.proyectorover;
}
