package lk.ijse.project.drivemaster.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.StudentBO;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentSearchController implements Initializable {


    @FXML
    private AnchorPane ancStudentSearch;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<StudentDTO, String> colNic;

    @FXML
    private TableColumn<StudentDTO, String> colAddress;

    @FXML
    private TableColumn<StudentDTO, LocalDate> colBirthday;

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
    private TextField txtSearch, textFirstName, textSecondName, textNic, textEmail, textContact, textAddress;

    @FXML
    private DatePicker textDateOfBirth, textJoinDate;
    @FXML
    private ChoiceBox<String> textGender;

    private final StudentBO studentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);


    @FXML
    void onActionDelete(ActionEvent event) {

    }

    @FXML
    void onActionMonth(ActionEvent event) {

    }

    @FXML
    void onActionPayment(ActionEvent event) {
        var selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "No Student Selected", "Please select a student before proceeding.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EnrolleeDetails.fxml"));
            AnchorPane anchorPane = loader.load();

            // Get controller and pass student
            EnrolleeDetailsController controller = loader.getController();
            controller.setStudent(selectedStudent);

            // Load into parent
            ancStudentSearch.getChildren().clear();
            anchorPane.prefWidthProperty().bind(ancStudentSearch.widthProperty());
            anchorPane.prefHeightProperty().bind(ancStudentSearch.heightProperty());
            ancStudentSearch.getChildren().add(anchorPane);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Page Not Found", "The requested page could not be loaded. Please check and try again.");
            e.printStackTrace();
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
        StudentDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            textFirstName.setText(selected.getFirstName());
            textSecondName.setText(selected.getLastName());
            textEmail.setText(selected.getEmail());
            textContact.setText(selected.getPhone());
            textAddress.setText(selected.getAddress());
            textGender.setValue(selected.getGender());
            textNic.setText(selected.getNic());
            textDateOfBirth.setValue(selected.getBirthday());
            textJoinDate.setValue(selected.getRegDate());

        }
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
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));


        try {
            loadTableData();
            initComboBoxes();
            clearStudentFields();
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

        String namePattern = "^[A-Za-z ]+$";
        boolean isValidFirstName = !firstName.isEmpty() && firstName.matches(namePattern);
        boolean isValidSecondName = !secondName.isEmpty() && secondName.matches(namePattern);
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        boolean isValidNic = !nic.isEmpty() && nic.matches(nicPattern);
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        boolean isValidEmail = !email.isEmpty() && email.matches(emailPattern);
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        boolean isValidPhone = !phone.isEmpty() && phone.matches(phonePattern);

        boolean isValidAddress = !address.isEmpty();
        boolean isValidGender = textGender.getValue() != null;


        if (!isValidFirstName) showErrorWithTimeout(textFirstName);
        if (!isValidSecondName) showErrorWithTimeout(textSecondName);
        if (!isValidNic) showErrorWithTimeout(textNic);
        if (!isValidEmail) showErrorWithTimeout(textEmail);
        if (!isValidPhone) showErrorWithTimeout(textContact);
        if (!isValidAddress) showErrorWithTimeout(textAddress);
        if (!isValidGender) showErrorWithTimeout(textGender);


        return isValidFirstName &&
                isValidSecondName &&
                isValidNic &&
                isValidEmail &&
                isValidPhone &&
                isValidAddress &&
                isValidGender;
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
                    textAddress.requestFocus();
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
                    textNic.requestFocus();
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
                    textEmail.requestFocus();
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
                textJoinDate.requestFocus();
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
                    textContact.requestFocus();
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
        textJoinDate.setValue(LocalDate.now());


    }

    private void initComboBoxes() {
        textGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        textMonth.setItems(FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));
        textMonth.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
        ObservableList<String> years = FXCollections.observableArrayList();

        int currentYear = LocalDate.now().getYear();
        for (int i = 2000; i <= currentYear; i++) {
            years.add(String.valueOf(i));
        }

        textYear.setItems(years);

        textYear.getSelectionModel().select(String.valueOf(currentYear));
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
                                StudentDTO.getNic(),
                                StudentDTO.getEmail(),
                                StudentDTO.getPhone(),
                                StudentDTO.getRegDate()
                        )).toList()
        ));

    }

    public void onRefresh(MouseEvent mouseEvent) {
        clearStudentFields();
    }
}
