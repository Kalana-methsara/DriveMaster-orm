package lk.ijse.project.drivemaster.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.project.drivemaster.bo.custom.*;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import lk.ijse.project.drivemaster.entity.Enrollment;
import lk.ijse.project.drivemaster.enums.PaymentStatus;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class StudentRegistrationController implements Initializable {

    @FXML
    private ComboBox cmdCourses;
    @FXML
    private TableColumn colCourseId, colCourseFee, colCourseName;
    @FXML
    private TableView tableSelectedCourses;
    @FXML
    private Label lblCourseId, lblCourseName, lblCourseDuration, lblCourseFee, lblSelectedCount, lblTotal, lblBalance;
    @FXML
    private DatePicker textDateOfBirth;
    @FXML
    private ChoiceBox<String> textGender, textCity, textProvince, textPaymentMethod;
    @FXML
    private TextField textFirstName, textSecondName, textNic, textEmail, textContact, textAddress, textFirstPayment;
    @FXML
    private Button btnSave, btnUpdate, btnDelete, btnAddCourse, btnConfirm, btnClear;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);
    private final StudentBO studentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);
    private final PaymentBO paymentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.PAYMENT);
    private final EnrollmentBO enrollmentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.ENROLLMENT);
    private final RegisterStudentBO registerStudentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.REGISTER);
    private final ObservableList<CourseDTO> selectedCourses = FXCollections.observableArrayList();




    public void onActionAddCourse(ActionEvent actionEvent) {
        CourseDTO selected = (CourseDTO) cmdCourses.getSelectionModel().getSelectedItem();
        if (selected != null && !selectedCourses.contains(selected)) {
            selectedCourses.add(selected);
            lblSelectedCount.setText(String.valueOf(selectedCourses.size()));

        }
        updateTotalFee();
        clearCourseFields();
    }

    private void clearCourseFields() {
        cmdCourses.getSelectionModel().clearSelection();
        lblCourseId.setText("");
        lblCourseName.setText("");
        lblCourseDuration.setText("");
        lblCourseFee.setText("");
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


    private void updateTotalFee() {
        double total = selectedCourses.stream()
                .mapToDouble(course -> course.getFee().doubleValue())
                .sum();
        lblTotal.setText(String.valueOf(total));
    }


    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-border-color: linear-gradient(#7b4397, #dc2430); -fx-border-width: 3px;");
        alert.showAndWait();
    }

    public void onActionConfirm(ActionEvent actionEvent) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }

        if (selectedCourses.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Courses Selected", "Please select at least one course.");
            return;
        }

        double total;
        double firstPayment;
        try {
            firstPayment = Double.parseDouble(textFirstPayment.getText());
            total = Double.parseDouble(lblTotal.getText());
        } catch (NumberFormatException | NullPointerException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid payment amount.");
            return;
        }

        if (firstPayment < total * 0.10) {
            showAlert(Alert.AlertType.WARNING, "Insufficient Payment", "Please pay at least 10% of the total fee.");
            return;
        }

        String paymentMethod = textPaymentMethod.getValue();
        if (paymentMethod == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Payment Method", "Please select a payment method.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Success", "Registration confirmed with: " + paymentMethod);


        String studentId = studentBO.getLastId();
        String firstName = textFirstName.getText() != null ? textFirstName.getText().trim() : "";
        String secondName = textSecondName.getText() != null ? textSecondName.getText().trim() : "";
        LocalDate birthday = textDateOfBirth.getValue();
        String gender = textGender.getValue();
        String address = textAddress.getText() != null ? textAddress.getText().trim() : "";
        String nic = textNic.getText();
        String email = textEmail.getText() != null ? textEmail.getText().trim() : "";
        String phone = textContact.getText() != null ? textContact.getText().trim() : "";
        LocalDate regDate = LocalDate.now();

        String enrollmentId = enrollmentBO.getLastId();
        List<EnrollmentDTO> enrollmentDTOS = new ArrayList<>();
        BigDecimal upfrontPaid = new BigDecimal(textFirstPayment.getText());

        ObservableList<CourseDTO> courses = tableSelectedCourses.getItems();
        for (CourseDTO course : courses) {
            EnrollmentDTO dto = new EnrollmentDTO(enrollmentId, studentId, course.getId(), regDate, upfrontPaid);
            enrollmentDTOS.add(dto);
        }


        String paymentId = paymentBO.getLastId();
        String method = textPaymentMethod.getValue();
        String status = String.valueOf(PaymentStatus.PENDING);
        String reference = "100"+paymentId;

        StudentDTO studentDTO = new StudentDTO(studentId, firstName, secondName, birthday, gender, address, nic, email, phone, regDate);
        PaymentDTO paymentDTO = new PaymentDTO(paymentId,studentId, upfrontPaid, method, LocalDateTime.now(), status, reference);

        try {
         boolean isRegister =  registerStudentBO.StudentRegistration(studentDTO, enrollmentDTOS, paymentDTO);
         if (isRegister){

         }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    public void onActionClear(ActionEvent actionEvent) {
        lblTotal.setText("0.00");
        textPaymentMethod.getSelectionModel().clearSelection();
        textFirstPayment.setText("");
        lblBalance.setText("0.00");
        lblSelectedCount.setText("0");
        tableSelectedCourses.getItems().clear();
        clearCourseFields();

    }


    @FXML
    void onKeyBalance(KeyEvent keyEvent) {
        try {
            double paid = textFirstPayment.getText().isEmpty() ? 0 : Double.parseDouble(textFirstPayment.getText());
            double total = Double.parseDouble(lblTotal.getText());
            lblBalance.setText(String.format("%.2f", total - paid));
        } catch (NumberFormatException | NullPointerException e) {
            lblBalance.setText("0.00");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBoxes();
        clearCourseFields();
        clearStudentFields();
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        tableSelectedCourses.setItems(selectedCourses);
        Platform.runLater(() -> textFirstName.requestFocus());

    }

    private void initComboBoxes() {
        textPaymentMethod.setItems(FXCollections.observableArrayList("Cash", "Card", "Online"));
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
        try {
            List<CourseDTO> itemList = courseBO.getAllCourses();
            cmdCourses.setItems(FXCollections.observableArrayList(itemList));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void onChooseCourse(ActionEvent mouseEvent) {
        CourseDTO selected = (CourseDTO) cmdCourses.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblCourseId.setText(selected.getId());
            lblCourseName.setText(selected.getName());
            lblCourseDuration.setText(selected.getDuration());
            lblCourseFee.setText(String.valueOf(selected.getFee()));


        }
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

    public void onRefresh(MouseEvent mouseEvent) {
        clearStudentFields();
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
}
