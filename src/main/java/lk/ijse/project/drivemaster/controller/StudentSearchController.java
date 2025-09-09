package lk.ijse.project.drivemaster.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
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
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.custom.StudentBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class StudentSearchController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<StudentDTO, String> colAddress;

    @FXML
    private TableColumn<StudentDTO, String> colBirthday;

    @FXML
    private TableColumn<StudentDTO, String> colContact;

    @FXML
    private TableColumn<StudentDTO, String> colEmail;

    @FXML
    private TableColumn<StudentDTO, String> colGender;

    @FXML
    private TableColumn<StudentDTO, LocalDate> colJoinDate;

    @FXML
    private TableColumn<StudentDTO, Long> colStudentId;

    @FXML
    private TableColumn<StudentDTO, String> colStudentName;

    @FXML
    private TableView<StudentDTO> tableView;

    @FXML
    private ComboBox<String> textMonth;

    @FXML
    private ComboBox<String> textYear;

    @FXML
    private TextField txtSearch,textFirstName, textSecondName, textNic, textEmail, textContact, textAddress, textFirstPayment;

    @FXML
    private DatePicker textDateOfBirth;
    @FXML
    private ChoiceBox<String> textGender, textCity, textProvince;


    private final String namePattern = "^[A-Za-z ]+$";
    private final String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";


    private final StudentBO studentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);


    @FXML
    void onActionDelete(ActionEvent event) {

    }

    @FXML
    void onActionMonth(ActionEvent event) {

    }

    @FXML
    void onActionSave(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }

    }

    @FXML
    void onActionUpdate(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }

    }

    @FXML
    void onActionYear(ActionEvent event) {

    }

    @FXML
    void setData(MouseEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("id"));


        colStudentName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName())
        );

        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));

        try {
            loadTableData();
//            initComboBoxes();
//            clearStudentFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateInputs() {
        String firstName = textFirstName.getText() != null ? textFirstName.getText().trim() : "";
        String secondName = textSecondName.getText() != null ? textSecondName.getText().trim() : "";
        String nic = textNic.getText() != null ? textNic.getText().trim() : "";
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";
        String phone = textContact.getText() != null ? textContact.getText().trim() : "";
        String address = textAddress.getText() != null ? textAddress.getText().trim() : "";

        boolean isValidFirstName = !firstName.isEmpty() && firstName.matches(namePattern);
        boolean isValidSecondName = !secondName.isEmpty() && secondName.matches(namePattern);
        boolean isValidNic = !nic.isEmpty() && nic.matches(nicPattern);
        boolean isValidEmail = !email.isEmpty() && email.matches(emailPattern);
        boolean isValidPhone = !phone.isEmpty() && phone.matches(phonePattern);

        boolean isValidAddress = !address.isEmpty();
        boolean isValidGender = textGender.getValue() != null;
        boolean isValidCity = textCity.getValue() != null;
        boolean isValidProvince = textProvince.getValue() != null;

        if (!isValidFirstName) showErrorWithTimeout(textFirstName);
        if (!isValidSecondName) showErrorWithTimeout(textSecondName);
        if (!isValidNic) showErrorWithTimeout(textNic);
        if (!isValidEmail) showErrorWithTimeout(textEmail);
        if (!isValidPhone) showErrorWithTimeout(textContact);
        if (!isValidAddress) showErrorWithTimeout(textAddress);
        if (!isValidGender) showErrorWithTimeout(textGender);
        if (!isValidCity) showErrorWithTimeout(textCity);
        if (!isValidProvince) showErrorWithTimeout(textProvince);

        return isValidFirstName &&
                isValidSecondName &&
                isValidNic &&
                isValidEmail &&
                isValidPhone &&
                isValidAddress &&
                isValidGender &&
                isValidCity &&
                isValidProvince;
    }

    private void showErrorWithTimeout(ChoiceBox<String> resetFieldStyles) {
        resetFieldStyles.setStyle("-fx-background-color: #f3e3e5; -fx-border-width: 0 0 2 0; -fx-border-color: #f3214b;");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    resetFieldStyles.setStyle("-fx-background-color: #e9f1f6; -fx-border-width: 0 0 2 0; -fx-border-color: #2196f3;");
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-border-color: linear-gradient(#7b4397, #dc2430); -fx-border-width: 3px;");
        alert.showAndWait();
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

    public void onKeyFirstName(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                if (textFirstName.getText().isEmpty()) {
                    showErrorWithTimeout(textFirstName);
                } else {
                    textSecondName.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textFirstName);
            }
        }
    }

    public void onKeySecondName(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                if (textSecondName.getText().isEmpty()) {
                    showErrorWithTimeout(textSecondName);
                } else {
                    textGender.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textSecondName);
            }
        }
    }


    public void onKeyNic(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                if (textNic.getText().isEmpty()) {
                    showErrorWithTimeout(textNic);
                } else {
                    textEmail.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textNic);
            }
        }
    }

    public void onKeyEmail(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                if (textEmail.getText().isEmpty()) {
                    showErrorWithTimeout(textEmail);
                } else {
                    textContact.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textEmail);
            }
        }
    }

    public void onKeyContact(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                if (textContact.getText().isEmpty()) {
                    showErrorWithTimeout(textContact);
                } else {
                    textDateOfBirth.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textContact);
            }
        }
    }

    public void onKeyBirthday(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                textAddress.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onKeyAddress(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                if (textAddress.getText().isEmpty()) {
                    showErrorWithTimeout(textAddress);
                } else {
                    textCity.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorWithTimeout(textAddress);
            }
        }
    }

    private void clearStudentFields() {
        textFirstName.clear();
        textSecondName.clear();
        textGender.getSelectionModel().clearSelection();
        textNic.clear();
        textEmail.clear();
        textContact.clear();
        textDateOfBirth.setValue(LocalDate.now());
        textAddress.clear();
        textCity.getSelectionModel().clearSelection();
        textProvince.getSelectionModel().clearSelection();


    }

    private void initComboBoxes() {
        textGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        textCity.setItems(FXCollections.observableArrayList(
                "Colombo", "Gampaha", "Kalutara",
                "Kandy", "Matale", "Nuwara Eliya",
                "Galle", "Matara", "Hambantota",
                "Jaffna", "Kilinochchi", "Mannar",
                "Vavuniya", "Mullaitivu", "Batticaloa",
                "Ampara", "Trincomalee", "Kurunegala",
                "Puttalam", "Anuradhapura", "Polonnaruwa",
                "Badulla", "Monaragala", "Ratnapura",
                "Kegalle"
        ));
        textProvince.setItems(FXCollections.observableArrayList(
                "Western",
                "Central",
                "Southern",
                "Northern",
                "Eastern",
                "North Western",
                "North Central",
                "Uva",
                "Sabaragamuwa"
        ));


    }


    private void loadTableData() throws Exception {
        tableView.setItems(FXCollections.observableArrayList(
                studentBO.getAllStudent().stream().map(StudentDTO ->
                        new StudentDTO(
                                StudentDTO.getId(),
                                StudentDTO.getFirstName(),
                                StudentDTO.getLastName(),
                                StudentDTO.getBirthday(),
                                StudentDTO.getGender(),
                                StudentDTO.getAddress(),
                                StudentDTO.getEmail(),
                                StudentDTO.getPhone(),
                                StudentDTO.getRegDate()
                        )).toList()
        ));

    }
}
