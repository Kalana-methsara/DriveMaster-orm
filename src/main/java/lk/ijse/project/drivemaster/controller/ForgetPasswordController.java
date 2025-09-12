package lk.ijse.project.drivemaster.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.UserBO;
import lk.ijse.project.drivemaster.dto.UserDTO;
import lk.ijse.project.drivemaster.util.SendMail;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForgetPasswordController implements Initializable {

    private final UserBO userBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.USER);
    public Label lblPassword;
    public ImageView showPassword;
    public Label lblPassword1;
    public TextField txtVisiblePassword1;
    public ImageView showPassword1;
    public PasswordField passwordField;
    public PasswordField passwordField1;
    public TextField txtVisiblePassword;


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

    private String generatedOTP;
    private String storedPassword;
    private String storedRole;

    private boolean isPasswordVisible = false;
    private boolean isPasswordVisible1 = false;


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

    public void onKeyOTP(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                sendOTP();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout("Error");
            }
        }
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
    private void showErrorWithTimeout1(String message) {
        lblError.setText(message);
        lblError.setVisible(true);
        setFieldStyleError1();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    lblError.setVisible(false);
                    setFieldStyleSuccess1();
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
    private void setFieldStyleError1() {
        String style = "-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color:  #dfe4ea;";
        lblPassword.setStyle(style);
        lblPassword1.setStyle(style);
    }

    private void setFieldStyleSuccess() {
        String style = "-fx-border-color: #3DAF3BFF; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color:  #dfe4ea;";
        lblUsername.setStyle(style);
        lblEmail.setStyle(style);
    }
    private void setFieldStyleSuccess1() {
        String style = "-fx-border-color: #3DAF3BFF; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color:  #dfe4ea;";
        lblPassword.setStyle(style);
        lblPassword1.setStyle(style);
    }

    public void onKeyCheck(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                verifyOTP();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout("Error");
            }
        }
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
    public void PasswordVisibility1(MouseEvent mouseEvent) {
        if (isPasswordVisible1) {
            passwordField1.setText(txtVisiblePassword1.getText());
            txtVisiblePassword1.setVisible(false);
            txtVisiblePassword1.setManaged(false);
            passwordField1.setVisible(true);
            passwordField1.setManaged(true);
            isPasswordVisible1 = false;
        } else {
            txtVisiblePassword1.setText(passwordField1.getText());
            passwordField1.setVisible(false);
            passwordField1.setManaged(false);
            txtVisiblePassword1.setVisible(true);
            txtVisiblePassword1.setManaged(true);
            isPasswordVisible1 = true;
        }

    }

    @FXML
    void onVerifyOTP() {
        verifyOTP();
    }

    @FXML
    void onSendOTP(ActionEvent event) {
        sendOTP();
    }

    @FXML
    public void onCheck(MouseEvent mouseEvent) {
        onVerifyOTP();
    }

    public void setLblCount() {
        lblCount.setVisible(true);
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                final int count = i;
                Platform.runLater(() -> lblCount.setText(String.valueOf(count)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> lblCount.setVisible(false));
        }).start();
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

    private void sendOTP() {
        String username = txtUserName.getText().trim();
        String email = txtEmail.getText().trim();
        String password;
        if (!isPasswordVisible) {
            password = passwordField.getText();
        } else {
            password = txtVisiblePassword.getText();
        }
        String confirmPassword;
        if (!isPasswordVisible1) {
            confirmPassword = passwordField1.getText();
        } else {
            confirmPassword = txtVisiblePassword1.getText();
        }

        if (username.isEmpty() || email.isEmpty()) {
            showErrorWithTimeout("Username or Email cannot be empty!");
            return;
        }
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            showErrorWithTimeout1("Password or ConformPassword cannot be empty!");
            return;
        }
        if (password.length() < 8 || confirmPassword.length() < 8) {
            showErrorWithTimeout1("Password and ConfirmPassword must be at least 8 characters!");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showErrorWithTimeout1("Passwords do not match!");
            return;
        }


        UserDTO user = userBO.findPassword(username, email);

        try {
            if (user != null) {
                setLblCount();

                boolean isUpdate =userBO.updatePassword(user.getId(),password);
                if (!isUpdate) {
                    showErrorWithTimeout("Password update failed. Please try again.");
                    return;
                }

                storedPassword = password;

                storedRole = user.getRole();

                generatedOTP = String.valueOf((int) (Math.random() * 900000) + 100000);

                String message = String.format("""
                        Hello %s,
                        
                        Here is your OTP for resetting your password:
                        OTP: %s
                        
                        Please use this OTP to reset your password.
                        
                        Regards,
                        Drive Master Team
                        """, username, generatedOTP);

                new OTPSender(message, email).start();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "OTP has been sent to your email!", ButtonType.OK);
                alert.initStyle(StageStyle.UNDECORATED);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-border-color: #023c73; -fx-border-width: 2px;");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        lblOPT.setVisible(true);
                        txtOPT.setVisible(true);
                        lblCheck.setVisible(true);
                    }
                });

                setFieldStyleSuccess();

            } else {
                showErrorWithTimeout("Invalid Username or Email!");
            }

        } catch (Exception e) {
            Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, e);
            showAlert(Alert.AlertType.ERROR, "An error occurred while sending OTP. Please try again later.");
        }
    }

    private void verifyOTP() {
        String enteredOTP = txtOPT.getText().trim();
        String username = txtUserName.getText().trim();
        String email = txtEmail.getText().trim();

        if (enteredOTP.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter the OTP!");
            return;
        }

        if (enteredOTP.equals(generatedOTP)) {
            String message = String.format("""
                    Dear %s,
                    
                    Your login details for the Drive Master System are as follows:
                    Username: %s
                    Password: %s
                    Role: %s
                    
                    Please change your password after logging in for the first time.
                    
                    Regards,
                    System Admin
                    """, username, username, storedPassword, storedRole);

            try {
                new CredentialSender(message, email).start();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login credentials have been sent to your email!", ButtonType.OK);
                alert.initStyle(StageStyle.UNDECORATED);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-border-color: #023c73; -fx-border-width: 2px;");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        loadLoginPage();
                    }
                });

            } catch (Exception e) {
                Logger.getLogger(ForgetPasswordController.class.getName()).log(Level.SEVERE, null, e);
                showAlert(Alert.AlertType.ERROR, "Failed to send email. Try again.");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid OTP. Please try again!");
        }
    }

    public void onKeyPassword(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                passwordField.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout("Error");
            }
        }
    }

    public void onKeyConformPassword(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                passwordField1.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout("Error");
            }
        }
    }


    class OTPSender extends Thread {
        private final String message;
        private final String email;

        public OTPSender(String message, String email) {
            this.message = message;
            this.email = email;
        }

        @Override
        public void run() {
            try {
                SendMail.outMail(message, email, "Password Reset OTP");
            } catch (MessagingException e) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Failed to send OTP. Please try again!"));
            }
        }
    }

    class CredentialSender extends Thread {
        private final String message;
        private final String email;

        public CredentialSender(String message, String email) {
            this.message = message;
            this.email = email;
        }

        @Override
        public void run() {
            try {
                SendMail.outMail(message, email, "Login Credentials - Dry Ice Management System");
            } catch (MessagingException e) {
                Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Failed to send login credentials. Please try again!"));
            }
        }
    }
}


