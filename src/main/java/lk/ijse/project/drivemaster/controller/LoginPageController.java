package lk.ijse.project.drivemaster.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.UserBO;
import lk.ijse.project.drivemaster.dto.Session;
import lk.ijse.project.drivemaster.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    private final UserBO userBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.USER);

    public ImageView showPassword;
    @FXML
    private Hyperlink lblError;

    @FXML
    private Label lblUsername, lblPassword;

    @FXML
    private AnchorPane ancLogin;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField txtVisiblePassword;

    private boolean isPasswordVisible = false;

    @FXML
    private void initialize() {
        lblError.setVisible(false);
        javafx.application.Platform.runLater(() -> txtUserName.requestFocus());
    }


    public void PasswordVisibility(MouseEvent mouseEvent) {
        if (isPasswordVisible) {
            passwordField.setText(txtVisiblePassword.getText());
            txtVisiblePassword.setVisible(false);
            txtVisiblePassword.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            isPasswordVisible = false;
        } else {
            txtVisiblePassword.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            txtVisiblePassword.setVisible(true);
            txtVisiblePassword.setManaged(true);
            isPasswordVisible = true;
        }

    }

    public void onKeySinging(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                signIn();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout();
            }
        }
    }

    public void onForgotPasswordAction(ActionEvent actionEvent) {
        try {
            ancLogin.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/ForgetPassword.fxml"));
            anchorPane.prefWidthProperty().bind(ancLogin.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLogin.heightProperty());
            ancLogin.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found", ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    public void onKeyPassword(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                passwordField.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout();
            }
        }

    }

    public void onSignAction(ActionEvent actionEvent) throws IOException {
        signIn();

    }
    private void signIn() throws IOException {
        String userName = txtUserName.getText();
        String password;
        if (!isPasswordVisible) {
            password = passwordField.getText();
        } else {
            password = txtVisiblePassword.getText();
        }

        if (userName.isEmpty() || password.isEmpty()) {
            showErrorWithTimeout();
            return;
        }
        UserDTO user = userBO.searchUser(userName, password);


        if (user != null) {
            Session.setCurrentUser(user);

            ancLogin.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
            ancLogin.getChildren().add(parent);
        } else {
            showErrorWithTimeout();
        }
    }
    private void showErrorWithTimeout() {
        lblError.setVisible(true);
        resetFieldStyles();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    lblError.setVisible(false);
                    resetFieldStyles1();
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    private void resetFieldStyles() {
        lblUsername.setStyle("-fx-background-color: #dfe4ea; -fx-border-color: RED; -fx-border-radius: 10; -fx-background-radius: 10;");
        lblPassword.setStyle("-fx-background-color: #dfe4ea; -fx-border-color: RED; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    private void resetFieldStyles1() {
        lblUsername.setStyle("-fx-background-color: #dfe4ea; -fx-border-color:  #023c73; -fx-border-radius: 10; -fx-background-radius: 10;");
        lblPassword.setStyle("-fx-background-color: #dfe4ea; -fx-border-color:  #023c73; -fx-border-radius: 10; -fx-background-radius: 10;");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
        javafx.application.Platform.runLater(() -> txtUserName.requestFocus());
    }
}
