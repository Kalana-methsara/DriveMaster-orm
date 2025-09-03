package lk.ijse.project.drivemaster.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForgetPasswordController implements Initializable {
    @FXML
    private Label lblCount;
    @FXML
    private ImageView lblCheck;
    @FXML
    private TextField txtOPT;
    @FXML
    private Label lblOPT;
    @FXML
    private Label lblEmail;
    @FXML
    private Button btnSendOPT;
    @FXML
    private Hyperlink lblError;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtUserName;
    @FXML
    private Label lblUsername;
    @FXML
    private AnchorPane ancFoget;

    public void onKeyEmail(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                txtEmail.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout("Error");
            }
        }
    }

    public void onKeyCheck(KeyEvent keyEvent) {
    }

    public void onKeyOTP(KeyEvent keyEvent) {
    }

    @FXML
    void closeOnAction(MouseEvent mouseEvent) {
        loadLoginPage();
    }
    private void loadLoginPage() {
        try {
            ancFoget.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            anchorPane.prefWidthProperty().bind(ancFoget.widthProperty());
            anchorPane.prefHeightProperty().bind(ancFoget.heightProperty());
            ancFoget.getChildren().add(anchorPane);
        } catch (Exception e) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, e);
            showAlert(Alert.AlertType.ERROR, "Unable to load login page!");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();

        if (alertType == Alert.AlertType.ERROR || alertType == Alert.AlertType.WARNING) {
            dialogPane.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            dialogPane.setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
        }

        alert.show();
    }
    private void showErrorWithTimeout(String message) {
        lblError.setText(message);
        lblError.setVisible(true);
        setFieldStyleError();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    lblError.setVisible(false);
                    setFieldStyleSuccess();
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    private void setFieldStyleError() {
        String style = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color:  #dfe4ea;";
        lblUsername.setStyle(style);
        lblEmail.setStyle(style);
    }

    private void setFieldStyleSuccess() {
        String style = "-fx-border-color: #3DAF3BFF; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color:  #dfe4ea;";
        lblUsername.setStyle(style);
        lblEmail.setStyle(style);
    }

    public void onCheck(MouseEvent mouseEvent) {
    }

    public void onSendOTP(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
        lblOPT.setVisible(false);
        txtOPT.setVisible(false);
        lblCheck.setVisible(false);
        lblCount.setVisible(false);
        Platform.runLater(() -> txtUserName.requestFocus());
    }
}
