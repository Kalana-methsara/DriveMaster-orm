package lk.ijse.project.drivemaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoadinScreen.fxml"));
        Parent load = fxmlLoader.load();
        Scene scene = new Scene(load);
        stage.setTitle("Driver Master App");
        stage.setScene(scene);
        stage.setMinWidth(1300);
        stage.setMinHeight(600);
        stage.show();

    }
}

