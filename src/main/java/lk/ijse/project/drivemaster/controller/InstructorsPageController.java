package lk.ijse.project.drivemaster.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;

import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructorsPageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<InstructorDTO, String> colContact;

    @FXML
    private TableColumn<InstructorDTO, String> colEmail;

    @FXML
    private TableColumn<InstructorDTO, String> colNic;

    @FXML
    private TableColumn<InstructorDTO, Long> colInstructorId;

    @FXML
    private TableColumn<InstructorDTO, String> colInstructorName;

    @FXML
    private TableView<InstructorDTO> tableView;

    @FXML
    private TextField textContact;

    @FXML
    private TextField textEmail;

    @FXML
    private Label lblId;

    @FXML
    private TextField textNic;

    @FXML
    private TextField textName;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

    private final InstructorBO instructorBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.INSTRUCTOR);


    @FXML
    void onActionDelete(ActionEvent event) {
        var selectedInstructor = tableView.getSelectionModel().getSelectedItem();
        if (selectedInstructor == null) {
            showAlert(Alert.AlertType.WARNING, "No Instructor Selected", "Please select a instructor before proceeding.");
            return;
        }
        boolean confirmed = showConfirmation("Confirm Delete", "Are you sure you want to delete this instructor?");
        if (confirmed) {
            try {
                String instructorId = lblId.getText();
                boolean isDeleted = instructorBO.deleteInstructor(instructorId);

                if (isDeleted) {
                    clearInstructorFields();
                    loadTableData();
//                    showAlert(Alert.AlertType.INFORMATION, "Success", "Instructor deleted successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "No instructor found with this ID.");
                }
            } catch (InUseException e) {
                showAlert(Alert.AlertType.ERROR, "In Use", "This instructor is currently in use and cannot be deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete instructor. Please try again!");
            }
        }
    }

    @FXML
    void onActionSave(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }
        String id = lblId.getText() != null ? lblId.getText().trim() : "";
        String name = textName.getText() != null ? textName.getText().trim() : "";
        String nic = textNic.getText() != null ? textNic.getText().trim() : "";
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";
        String phone = textContact.getText() != null ? textContact.getText().trim() : "";

        InstructorDTO instructorDTO = new InstructorDTO(id, name, nic, email, phone);

        try {
            instructorBO.saveInstructor(instructorDTO);
            clearInstructorFields();
            loadTableData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Instructor saved successfully!");
        } catch (DuplicateException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Duplicate Instructor", "A instructor with this ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Save", "Failed to save instructor. Please try again!");
        }
    }
    @FXML
    void onActionUpdate(ActionEvent event) {
        var selectedInstructor = tableView.getSelectionModel().getSelectedItem();
        if (selectedInstructor == null) {
            showAlert(Alert.AlertType.WARNING, "No Instructor Selected", "Please select a instructor before proceeding.");
            return;
        }
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }
        String id = lblId.getText() != null ? lblId.getText().trim() : "";
        String name = textName.getText() != null ? textName.getText().trim() : "";
        String nic = textNic.getText() != null ? textNic.getText().trim() : "";
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";
        String phone = textContact.getText() != null ? textContact.getText().trim() : "";

        InstructorDTO instructorDTO = new InstructorDTO(id, name, nic, email, phone);

        try {
            instructorBO.updateInstructor(instructorDTO);
            clearInstructorFields();
            loadTableData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Instructor updated successfully!");
        } catch (NotFoundException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Instructor Not Found", "No instructor found with this ID!");
        } catch (DuplicateException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Duplicate Instructor", "A instructor with this name or ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Update", "Failed to update instructor. Please try again!");
        }
    }

    private boolean validateInputs() {
        String name = textName.getText() != null ? textName.getText().trim() : "";
        String nic = textNic.getText() != null ? textNic.getText().trim() : "";
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";
        String phone = textContact.getText() != null ? textContact.getText().trim() : "";

        boolean isValidName = !name.isEmpty() && name.matches(namePattern);
        boolean isValidNic = !nic.isEmpty() && nic.matches(nicPattern);
        boolean isValidEmail = !email.isEmpty() && email.matches(emailPattern);
        boolean isValidPhone = !phone.isEmpty() && phone.matches(phonePattern);

        if (!isValidName) showErrorWithTimeout(textName);
        if (!isValidNic) showErrorWithTimeout(textNic);
        if (!isValidEmail) showErrorWithTimeout(textEmail);
        if (!isValidPhone) showErrorWithTimeout(textContact);


        return isValidName &&
                isValidNic &&
                isValidEmail &&
                isValidPhone;
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


    @FXML
    void onKeyContact(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                textEmail.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textContact);
            }
        }
    }


    @FXML
    void onKeyNic(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                textContact.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textNic);
            }
        }
    }

    @FXML
    void onKeyName(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                textNic.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textName);
            }
        }
    }

    @FXML
    void onRefresh(MouseEvent event) {
        clearInstructorFields();
    }

    @FXML
    void setData(MouseEvent event) {
        InstructorDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(String.valueOf(selected.getId()));
            textName.setText(selected.getName());
            textNic.setText(selected.getNic());
            textEmail.setText(selected.getEmail());
            textContact.setText(selected.getPhone());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colInstructorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            loadTableData();
            clearInstructorFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        javafx.application.Platform.runLater(() -> textName.requestFocus());

    }



    private void clearInstructorFields() {
        loadNextId();
        textName.clear();
        textNic.clear();
        textContact.clear();
        textEmail.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private void loadNextId() {
        String nextId = String.valueOf(instructorBO.getNextId());
        lblId.setText(nextId);
    }

    private void loadTableData() throws Exception {
        tableView.setItems(FXCollections.observableArrayList(
                instructorBO.getAllInstructors().stream().map(InstructorDTO ->
                        new InstructorDTO(
                                InstructorDTO.getId(),
                                InstructorDTO.getName(),
                                InstructorDTO.getNic(),
                                InstructorDTO.getEmail(),
                                InstructorDTO.getPhone()
                        )).toList()
        ));
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

}
