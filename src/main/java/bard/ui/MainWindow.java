package bard.ui;

import bard.Bard;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Bard bard;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image bardImage = new Image(this.getClass().getResourceAsStream("/images/Bard_circular_icon.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Bard instance */
    public void setBard(Bard b) {
        bard = b;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Bard's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = bard.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getBardDialog(response, bardImage)
        );
        userInput.clear();

        // Check if the exit flag has been set by an ExitCommand
        if (bard.hasExited()) {
            // Use a delay to let the UI show the goodbye message
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> {
                Platform.exit();  // Gracefully shuts down JavaFX
                System.exit(0);   // Ensures the JVM terminates
            });
            delay.play();
        }
    }
}
