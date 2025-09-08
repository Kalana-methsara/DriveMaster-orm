package lk.ijse.project.drivemaster.controller;

import lk.ijse.project.drivemaster.dto.UserDTO;
import lk.ijse.project.drivemaster.dto.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML
    private AnchorPane ancMainContainer;

    @FXML
    private AnchorPane ancUserView;

    @FXML
    private Button btnAdminManage, btnBooking, btnDashboard, btnStudent, btnPayment,
             btnRegister, btnSalary, btnTransport, btnInventory, btnStock, btnLogout;

    @FXML
    private Label lblDate;

    @FXML
    private ImageView pngAdminManage, pngBooking, pngStudent, pngPayment,
            pngRegister, pngSalary, pngTransport, pngDashboard, pngInventory, pngStock, pngLogout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        navigateTo("/view/DashboardPage.fxml");

        UserDTO user = Session.getCurrentUser();
        String role = user.getRole();

        switch (role) {
            case "ADMIN":
                break;
            case "RECEPTIONIST":
                btnAdminManage.setDisable(true);
                break;
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        lblDate.setText(currentDate.format(formatter));
    }

    @FXML
    void onDashboard(ActionEvent event) {
        resetOtherPages();
        changePage1(btnDashboard, "/images/dashboard(1).png", pngDashboard);
        navigateTo("/view/DashboardPage.fxml");
    }

    @FXML
    void onRegistration(ActionEvent event) {
        resetOtherPages();
        changePage1(btnRegister, "/images/registration(1).png", pngRegister);
        navigateTo("/view/StudentRegistration.fxml");
    }

    @FXML
    void onStudent(ActionEvent event) {
        resetOtherPages();
        changePage1(btnStudent, "/images/students(1).png", pngStudent);
        navigateTo("/view/StudentSearch.fxml");
    }

    @FXML
    void onTransport(ActionEvent event) {
        resetOtherPages();
        changePage1(btnTransport, "/images/deliver(1).png", pngTransport);
        navigateTo("/view/PaymentPage.fxml");
    }

    @FXML
    void onBooking(ActionEvent event) {
        resetOtherPages();
        changePage1(btnBooking, "/images/booking(1).png", pngBooking);
        navigateTo("/view/EnrolleeDetails.fxml");
    }

    @FXML
    void onStock(ActionEvent event) {
        resetOtherPages();
        changePage1(btnStock, "/images/stock(1).png", pngStock);
        navigateTo("/view/StockPage.fxml");
    }

    @FXML
    void onSalary(ActionEvent event) {
        resetOtherPages();
        changePage1(btnSalary, "/images/salary(1).png", pngSalary);
        navigateTo("/view/SalaryPage.fxml");
    }

    @FXML
    void onAdminManage(ActionEvent event) {
        resetOtherPages();
        changePage1(btnAdminManage, "/images/admin(1).png", pngAdminManage);
        navigateTo("/view/VerifySuperAdmin.fxml");
    }

    public void onPayment(ActionEvent actionEvent) {
        resetOtherPages();
        changePage1(btnPayment, "/images/payment(1).png", pngPayment);
        navigateTo("/view/PaymentPage.fxml");
    }

    public void onInventory(ActionEvent actionEvent) {
        resetOtherPages();
        changePage1(btnInventory, "/images/inventory(1).png", pngInventory);
        navigateTo("/view/InventoryPage.fxml");
    }


    private void resetOtherPages() {
        resetButtonStyle(btnDashboard);
        resetButtonStyle(btnRegister);
        resetButtonStyle(btnStudent);
        resetButtonStyle(btnTransport);
        resetButtonStyle(btnBooking);
        resetButtonStyle(btnPayment);
        resetButtonStyle(btnSalary);
        resetButtonStyle(btnStock);
        resetButtonStyle(btnInventory);
        resetButtonStyle(btnAdminManage);

        changePage("/images/dashboard.png", pngDashboard);
        changePage("/images/registration.png", pngRegister);
        changePage("/images/students.png", pngStudent);
        changePage("/images/deliver.png", pngTransport);
        changePage("/images/booking.png", pngBooking);
        changePage("/images/payment.png", pngPayment);
        changePage("/images/salary.png", pngSalary);
        changePage("/images/stock.png", pngStock);
        changePage("/images/key.png", pngInventory);
        changePage("/images/admin.png", pngAdminManage);

    }

    private void resetButtonStyle(Button button) {
        button.setStyle("-fx-text-fill: white; -fx-background-color: #228b22; -fx-border-color: #98ff98; -fx-border-radius: 10px;");
    }

    private void setActiveButtonStyle(Button button) {
        button.setStyle("-fx-text-fill: #e9c90e; -fx-background-color: #228b22; -fx-border-color: #e9c90e; -fx-border-radius: 10px;");
    }

    private void changePage(String imagePath, ImageView imageView) {
        imageView.setImage(new Image(imagePath));
    }

    private void changePage1(Button button, String selectedImagePath, ImageView imageView) {
        imageView.setImage(new Image(selectedImagePath));
        setActiveButtonStyle(button);
    }

    public void navigateTo(String path) {
        try {
            ancMainContainer.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());
            ancMainContainer.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found", ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    public void onLoginUser(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/LoginUserDetails.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void onLogout(ActionEvent actionEvent) {
        // Confirm logout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);

        alert.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // Clear the session
                Session.setCurrentUser(null);

                try {
                    // Load the login page
                    AnchorPane loginPane = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
                    ancMainContainer.getScene().setRoot(loginPane);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to load login page", ButtonType.OK).show();
                    e.printStackTrace();
                }
            }
        });
    }
}

