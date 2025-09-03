package lk.ijse.project.drivemaster.controller;


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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
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

    @FXML
    private Button btnSignIn;

    private boolean isPasswordVisible = false;

    @FXML
    private void initialize() {

    }


    public void PasswordVisibility(MouseEvent mouseEvent) {
    }

    public void onKeySinging(KeyEvent keyEvent) {
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
    }

    public void onSignAction(ActionEvent actionEvent) throws IOException {
        ancLogin.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/UserView.fxml"));
        ancLogin.getChildren().add(parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblError.setVisible(false);
        javafx.application.Platform.runLater(() -> txtUserName.requestFocus());
    }
}
