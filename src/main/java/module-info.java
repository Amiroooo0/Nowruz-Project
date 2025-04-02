module back.genus {
    requires javafx.controls;
    requires javafx.fxml;


    opens back.genus to javafx.fxml;
    exports back.genus;
}