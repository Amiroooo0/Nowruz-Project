package back.genus;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GeniusController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}