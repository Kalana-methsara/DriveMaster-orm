package lk.ijse.project.drivemaster.controller;

import jakarta.persistence.PersistenceException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.custom.EnrollmentBO;
import lk.ijse.project.drivemaster.bo.custom.LessonBO;
import lk.ijse.project.drivemaster.bo.custom.PaymentBO;
import lk.ijse.project.drivemaster.dto.*;
import lk.ijse.project.drivemaster.enums.PaymentStatus;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import lk.ijse.project.drivemaster.bo.custom.*;
import lk.ijse.project.drivemaster.dto.*;


public class AddCourseController implements Initializable {

    public Label lblCourseName;
    public Label lblCourseId;
    public Label lblCourseDuration;
    public Label lblCourseFee;
    public Label lblSelectedCount;
    public TableView tableSelectedCourses;
    public Button btnAddCourse;
    @FXML
    private TableColumn colCourseId, colCourseFee, colCourseName;
    @FXML
    private Label lblStudentId;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnConfirm;


    @FXML
    private Button btnPayment;

    @FXML
    private Button btnPrevCourse;

    @FXML
    private Button btnPrint;

    @FXML
    private Button btnUpdateStatus;

    @FXML
    private ComboBox cmdCourses;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblBirthday;

    @FXML
    private Label lblContact;
    @FXML
    private Label lblEmail;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblJoinDate;

    @FXML
    private Label lblNic;

    @FXML
    private Label lblStudentName;

    @FXML
    private Label lblTotal;


    @FXML
    private TextField textPayment;

    @FXML
    private ChoiceBox<String> textPaymentMethod;

    private StudentDTO student;

    private final PaymentBO paymentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.PAYMENT);
    private final EnrollmentBO enrollmentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.ENROLLMENT);
    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);
    private final ObservableList<CourseDTO> selectedCourses = FXCollections.observableArrayList();
    private final ObservableList<EnrollmentDTO> courseList = FXCollections.observableArrayList();
    private final RegisterStudentBO registerStudentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.REGISTER);

    public void setStudent(StudentDTO student) {
        this.student = student;
        setStudentData();
        loadCourse();


    }


    private final List<EnrollmentDTO> courses = new ArrayList<>();
    private int currentIndex = 0;


    @FXML
    void onActionClear(ActionEvent event) {
        clearPaymentFields();
    }

    @FXML
    void onActionConfirm(ActionEvent event) {

        if (selectedCourses.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Courses Selected", "Please select at least one course.");
            return;
        }

        double total;
        double firstPayment;
        try {
            firstPayment = Double.parseDouble(textPayment.getText());
            total = Double.parseDouble(lblTotal.getText());
        } catch (NumberFormatException | NullPointerException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid payment amount.");
            return;
        }

        if (firstPayment < total * 0.10) {
            showAlert(Alert.AlertType.WARNING, "Insufficient Payment", "Please pay at least 10% of the total fee.");
            return;
        }
        String balanceText = lblBalance.getText().trim();

        if (balanceText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Balance", "Balance cannot be empty.");
            return;
        }

        try {
            BigDecimal balance = new BigDecimal(balanceText);

            if (balance.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert(Alert.AlertType.ERROR, "Payment Over", "Please enter a balance greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Balance", "Balance must be a valid number.");
        }

        String paymentMethod = textPaymentMethod.getValue();
        if (paymentMethod == null) {
            showAlert(Alert.AlertType.WARNING, "Missing Payment Method", "Please select a payment method.");
            return;
        }

        String studentId = lblStudentId.getText();
        String fullName = lblStudentName.getText().trim();
        String[] parts = fullName.split(" ");

        String firstName = parts[0];
        String secondName = (parts.length > 1) ? parts[1] : "";

        LocalDate birthday = LocalDate.parse(lblBirthday.getText());
        String gender = lblGender.getText();
        String address = lblAddress.getText();
        String nic = lblNic.getText();
        String email = lblEmail.getText();
        String phone = lblContact.getText();
        LocalDate regDate = LocalDate.now();

        ArrayList<EnrollmentDTO> enrollmentDTOS = new ArrayList<>();
        for (EnrollmentDTO course : courseList) {
            enrollmentDTOS.add(new EnrollmentDTO(course));
        }

        String paymentId = paymentBO.getNextId();
        BigDecimal upfrontPaid = new BigDecimal(textPayment.getText());
        String method = textPaymentMethod.getValue();
        LocalDateTime now = LocalDateTime.now().withNano(0);
        String status = String.valueOf(PaymentStatus.COMPLETE);
        String reference = "RCPT-" + System.currentTimeMillis();

        StudentDTO studentDTO = new StudentDTO(studentId, firstName, secondName, birthday, gender, address, nic, email, phone, regDate);
        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentId, upfrontPaid, method, now, status, reference);

        try {
            boolean isRegister = registerStudentBO.UpdateRegistration(studentDTO, enrollmentDTOS, paymentDTO);
            if (isRegister) {

                clearCourseFields();
                clearPaymentFields();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Successful.");

            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Unexpected Error", "Something went wrong. Contact admin.");
            e.printStackTrace();
        }


    }

    private void updateTotalFee() {
        double total = selectedCourses.stream()
                .mapToDouble(course -> course.getFee().doubleValue())
                .sum();
        lblTotal.setText(String.valueOf(total));
    }

    private void clearPaymentFields() {
        lblTotal.setText("0.00");
        textPaymentMethod.getSelectionModel().clearSelection();
        textPayment.setText("");
        lblBalance.setText("0.00");
        lblSelectedCount.setText("0");
        tableSelectedCourses.getItems().clear();
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
    void onKeyBalance(KeyEvent event) {
        try {
            double paid = textPayment.getText().isEmpty() ? 0 : Double.parseDouble(textPayment.getText());
            double total = Double.parseDouble(lblTotal.getText());
            lblBalance.setText(String.format("%.2f", total - paid));
        } catch (NumberFormatException | NullPointerException e) {
            lblBalance.setText("0.00");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textPaymentMethod.setItems(FXCollections.observableArrayList("Cash", "Card", "Online"));

        colCourseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        try {
            List<CourseDTO> itemList = courseBO.getAllCourses();
            cmdCourses.setItems(FXCollections.observableArrayList(itemList));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tableSelectedCourses.setItems(selectedCourses);


    }

    private void loadCourse() {
        if (student == null) return;
        List<EnrollmentDTO> studentCourses = enrollmentBO.getStudentCourses(student.getId());
        courses.clear();
        courses.addAll(studentCourses);
    }


    private void setStudentData() {
        if (student == null) return;
        lblStudentId.setText(student.getId());
        lblStudentName.setText(student.getFirstName() + " " + student.getLastName());
        lblGender.setText(student.getGender());
        lblBirthday.setText(String.valueOf(student.getBirthday()));
        lblContact.setText(student.getPhone());
        lblNic.setText(student.getNic());
        lblJoinDate.setText(String.valueOf(student.getRegDate()));
        lblEmail.setText(student.getEmail());
        lblAddress.setText(student.getAddress());

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

    public void onActionAddCourse(ActionEvent actionEvent) {
        String studentId = lblStudentId.getText();
        LocalDate regDate = LocalDate.now();
        String nextId = enrollmentBO.getNextId(); // e.g. E003
        int baseNumber = Integer.parseInt(nextId.substring(1)); // 3
        int count = Integer.parseInt(lblSelectedCount.getText());

        int newNumber = baseNumber + count;
        String enrollmentId = String.format("E%03d", newNumber);

        CourseDTO selected = (CourseDTO) cmdCourses.getSelectionModel().getSelectedItem();

        if (enrollmentBO.getStudentDuplicateCourses(studentId, selected.getId())) {
            showAlert(Alert.AlertType.ERROR, "Error", "Registration Before this corse.");
            return;
        }
        if (selected != null && !selectedCourses.contains(selected)) {
            selectedCourses.add(selected);
            lblSelectedCount.setText(String.valueOf(selectedCourses.size()));
            EnrollmentDTO course = new EnrollmentDTO(enrollmentId, studentId, selected.getId(), regDate);
            courseList.add(course);
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
}
