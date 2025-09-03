package lk.ijse.project.drivemaster.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class LoadingScreenController {

    @FXML
    private AnchorPane ancLoading;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label lblLoading;

    @FXML
    private Label lblStatus;

    @FXML
    private Label percentageLabel;



    private final String[] loadingMessages = {
            "Loading core modules...",
            "Initializing database connection...",
            "Setting up user interface...",
            "Preparing dashboard...",
            "Optimizing performance...",
            "Finalizing setup..."
    };

    private final String[] statusMessages = {
            "This will only take a moment",
            "DriveMaster is getting ready",
            "Almost there, preparing your workspace",
            "Just a few more seconds",
            "Hang tight while we set things up",
            "Final touches in progress"
    };

    private final Random random = new Random();
    private Timeline timeline;
    private int currentStep = 0;

    public void initialize() {
        setupAnimations();
        startLoadingSimulation();
    }

    private void setupAnimations() {
        // Pulse animation for the progress bar
        ScaleTransition pulse = new ScaleTransition(Duration.millis(1000), progressBar);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.01);
        pulse.setToY(1.01);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();
    }

    private void startLoadingSimulation() {
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(80), event -> {
            double currentProgress = progressBar.getProgress();

            // Smooth progress with slight randomization
            double increment = 0.0;
            if (currentProgress < 0.3) {
                increment = 0.015 + (random.nextDouble() * 0.01);
            } else if (currentProgress < 0.7) {
                increment = 0.01 + (random.nextDouble() * 0.008);
            } else {
                increment = 0.005 + (random.nextDouble() * 0.005);
            }

            double newProgress = Math.min(1.0, currentProgress + increment);
            progressBar.setProgress(newProgress);

            int percent = (int) (newProgress * 100);
            percentageLabel.setText(percent + "%");

            // Update messages at certain intervals
            if (percent % 12 == 0) {
                updateLoadingMessages();
            }

            if (newProgress >= 1.0) {
                timeline.stop();
                lblLoading.setText("Setup Complete!");
                lblStatus.setText("Welcome to DriveMaster");

                // Transition to main application
                PauseTransition delay = new PauseTransition(Duration.seconds(1.2));
                delay.setOnFinished(e -> loadMainApplication());
                delay.play();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }






    private void updateLoadingMessages() {
        int newIndex = random.nextInt(loadingMessages.length);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), lblLoading);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            lblLoading.setText(loadingMessages[newIndex]);
            lblStatus.setText(statusMessages[random.nextInt(statusMessages.length)]);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), lblLoading);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    private void loadMainApplication() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ancLoading.getScene().getWindow(); // get current stage
            stage.setScene(scene); // set new scene
            stage.show();
        } catch (IOException e) {
            lblLoading.setText("Error loading application");
            lblStatus.setText("Please restart the application");
            e.printStackTrace();
        }
    }

}