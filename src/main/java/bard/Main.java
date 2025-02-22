package bard;

import java.io.IOException;

import bard.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Bard using FXML.
 */
public class Main extends Application {

    private Bard bard = new Bard();

    @Override
    public void start(Stage stage) {
        try {
            // Set the window title.
            stage.setTitle("Bard");

            Image icon =
                    new Image(getClass().getResourceAsStream("/images/Bard_circular_icon.png"));
            stage.getIcons().add(icon);

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBard(bard); // inject the Bard instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
