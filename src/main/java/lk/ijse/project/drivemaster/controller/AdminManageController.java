package lk.ijse.project.drivemaster.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.custom.UserBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.dto.InstructorDTO;
import lk.ijse.project.drivemaster.dto.UserDTO;
import lk.ijse.project.drivemaster.enums.Role;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminManageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<UserDTO, String> colEmail;

    @FXML
    private TableColumn<UserDTO, Role> colRole;

    @FXML
    private TableColumn<UserDTO, Long> colUserID;

    @FXML
    private TableColumn<UserDTO, String> colUserName;

    @FXML
    private TableView<UserDTO> tableView;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textPassword;

    @FXML
    private ChoiceBox<Role> textRole;

    @FXML
    private Label textUserId;

    @FXML
    private TextField textUserName;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


    private final UserBO userBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.USER);


    @FXML
    void onActionDelete(ActionEvent event) {
        boolean confirmed = showConfirmation("Confirm Delete", "Are you sure you want to delete this user?");
        if (confirmed) {
            try {
                String userId = textUserId.getText();
                boolean isDeleted = userBO.deleteUser(userId);

                if (isDeleted) {
                    clearUserFields();
                    loadTableData();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "No user found with this ID.");
                }
            } catch (InUseException e) {
                showAlert(Alert.AlertType.ERROR, "In Use", "This user is currently in use and cannot be deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user. Please try again!");
            }
        }
    }

    @FXML
    void onActionSave(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }
        String id = textUserId.getText() != null ? textUserId.getText().trim() : "";
        String name = textUserName.getText() != null ? textUserName.getText().trim() : "";
        String password = textPassword.getText() != null ? textPassword.getText().trim() : "";
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";
        String role = textRole.getValue() != null ? textRole.getValue().toString() : "";

        UserDTO userDTO = new UserDTO(id,name,password,email,role);
        try {
            userBO.saveUser(userDTO);
            clearUserFields();
            loadTableData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "User saved successfully!");
        } catch (DuplicateException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Duplicate User", "A user with this ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Save", "Failed to save user. Please try again!");
        }

    }


    @FXML
    void onActionUpdate(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }
        String id = textUserId.getText() != null ? textUserId.getText().trim() : "";
        String name = textUserName.getText() != null ? textUserName.getText().trim() : "";
        String password = textPassword.getText() != null ? textPassword.getText().trim() : "";
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";
        String role = textRole.getValue() != null ? textRole.getValue().toString() : "";

        UserDTO userDTO = new UserDTO(id,name,password,email,role);

        try {
            userBO.updateUser(userDTO);
            clearUserFields();
            loadTableData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
        } catch (NotFoundException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "User Not Found", "No user found with this ID!");
        } catch (DuplicateException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Duplicate User", "A user with this name or ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Update", "Failed to update user. Please try again!");
        }
    }

    private boolean validateInputs() {
        String name = textUserName.getText() != null ? textUserName.getText().trim() : "";
        String password = textPassword.getText() != null ? textPassword.getText().trim() : "";
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";


        boolean isValidEmail = !email.isEmpty() && email.matches(emailPattern);
        boolean isValidRole = textRole.getValue() != null;
        boolean isValidUserName = !name.isEmpty() && !name.contains(" ") && name.length() >= 5; // username min 3 chars
        boolean isValidPassword = !password.isEmpty() && !password.contains(" ") && password.length() >= 8; // password min 8 chars

        if (!isValidUserName) showErrorWithTimeout(textUserName);
        if (!isValidPassword) showErrorWithTimeout(textPassword);
        if (!isValidEmail) showErrorWithTimeout(textEmail);
        if (!isValidRole) showErrorWithTimeout(textRole);

        return isValidUserName && isValidPassword && isValidEmail && isValidRole;
    }



    private void showErrorWithTimeout(ChoiceBox<Role> resetFieldStyles) {
        resetFieldStyles.setStyle("-fx-background-color: #f3e3e5; -fx-border-width: 0 0 2 0; -fx-border-color: #f3214b;");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    resetFieldStyles.setStyle("-fx-background-color: #e9f1f6; -fx-border-width: 0 0 2 0; -fx-border-color: #2196f3;");
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    private void showErrorWithTimeout(TextField resetFieldStyles) {
        resetFieldStyles.setStyle("-fx-background-color: #f3e3e5; -fx-border-width: 0 0 2 0; -fx-border-color: #f3214b;");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    resetFieldStyles.setStyle("-fx-background-color: #e9f1f6; -fx-border-width: 0 0 2 0; -fx-border-color: #2196f3;");
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private boolean showConfirmation(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        alert.getDialogPane().setStyle(
                "-fx-border-color: linear-gradient(#7b4397, #dc2430); -fx-border-width: 3px;"
        );

        // Show and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-border-color: linear-gradient(#7b4397, #dc2430); -fx-border-width: 3px;");
        alert.showAndWait();
    }

    @FXML
    void onRefresh(MouseEvent event) {
        clearUserFields();

    }

    @FXML
    void setData(MouseEvent event) {
        UserDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            textUserId.setText(String.valueOf(selected.getId()));
            textUserName.setText(selected.getUsername());
            textEmail.setText(selected.getEmail());
            textRole.setValue(Role.valueOf(selected.getRole()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            initComboBoxes();
            loadTableData();
            clearUserFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initComboBoxes() {
        textRole.getItems().addAll(Role.values());
    }

    private void loadTableData() throws Exception {
        tableView.setItems(FXCollections.observableArrayList(
                userBO.getAllUsers().stream().map(UserDTO ->
                        new UserDTO(
                                UserDTO.getId(),
                                UserDTO.getUsername(),
                                UserDTO.getEmail(),
                                UserDTO.getRole()
                        )).toList()
        ));
    }

    private void loadNextId() {
        String nextId = userBO.getNextId();
        textUserId.setText(nextId);
    }

    private void clearUserFields() {
        loadNextId();
        textUserName.clear();
        textPassword.clear();
        textEmail.clear();
        textRole.getSelectionModel().clearSelection();
    }
}
