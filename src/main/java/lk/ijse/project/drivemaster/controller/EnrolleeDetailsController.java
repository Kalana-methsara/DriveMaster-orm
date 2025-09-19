package lk.ijse.project.drivemaster.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.*;
import lk.ijse.project.drivemaster.dto.*;
import lk.ijse.project.drivemaster.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EnrolleeDetailsController implements Initializable {

    @FXML
    private Label lblStudentId;
    @FXML
    private TableView<PaymentDTO> tablePayment;
    @FXML
    private TableView<LessonDTO> tableLesson;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colInTime;
    @FXML
    private TableColumn<?, ?> colOutTime;
    @FXML
    private Label lblNextCount;
    @FXML
    private Label lblPrevCount;
    @FXML
    private Button btnClear;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnNextCourse;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnPrevCourse;

    @FXML
    private Button btnPrint;

    @FXML
    private Button btnUpdateStatus;


    @FXML
    private TableColumn<?, ?> colDateTime;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPaidAmount;

    @FXML
    private TableColumn<?, ?> colPaymentMethod;

    @FXML
    private TableColumn<?, ?> colReceiptNumber;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblBirthday;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblCourseId;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblDuration;

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
    private Label lblEndDate;
    @FXML
    private Label lblStats;

    @FXML
    private TextField textPayment;

    @FXML
    private ChoiceBox<String> textPaymentMethod;

    private StudentDTO student;

    private final PaymentBO paymentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.PAYMENT);
    private final EnrollmentBO enrollmentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.ENROLLMENT);
    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);


    public void setStudent(StudentDTO student) {
        this.student = student;
        setStudentData();
        loadPaymentTableData();
        loadLessonTableData();
        loadCourse();
        showCourse(currentIndex);
        lblNextCount.setText(String.valueOf(courses.size() - 1));
        lblPrevCount.setText(String.valueOf(currentIndex));

    }


    private final List<EnrollmentDTO> courses = new ArrayList<>();
    private int currentIndex = 0;


    @FXML
    void onActionClear(ActionEvent event) {
        clearPaymentFields();
        loadLessonTableData();
    }

    @FXML
    void onActionConfirm(ActionEvent event) {
        String paymentId = paymentBO.getNextId();
        String studentId = lblStudentId.getText();
        BigDecimal upfrontPaid;

        try {
            upfrontPaid = new BigDecimal(textPayment.getText().trim());
        } catch (NumberFormatException | NullPointerException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid payment amount.");
            return;
        }

        String method = textPaymentMethod.getValue();

        if (upfrontPaid.compareTo(BigDecimal.ZERO) <= 0 || method == null || method.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }

        LocalDateTime now = LocalDateTime.now().withNano(0);
        String status = PaymentStatus.COMPLETE.name(); // cleaner
        String reference = "RCPT-" + System.currentTimeMillis();

        PaymentDTO paymentDTO = new PaymentDTO(paymentId, studentId, upfrontPaid, method, now, status, reference);

        try {
            boolean isSuccess = paymentBO.savePayment(paymentDTO);
            if (isSuccess) {
                clearPaymentFields();
                loadPaymentTableData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Payment Successful.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to save payment", e);
        }

    }

    private void clearPaymentFields() {
        textPaymentMethod.getSelectionModel().clearSelection();
        textPayment.setText("");
        lblBalance.setText("0.00");
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
    void onActionCourse(ActionEvent event) {

    }

    @FXML
    void onActionPrint(ActionEvent event) {

    }

    @FXML
    void onActionUpdateStatus(ActionEvent event) {

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
        btnPrevCourse.setOnAction(e -> showPreviousCourse());
        btnNextCourse.setOnAction(e -> showNextCourse());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateTime.setCellValueFactory(new PropertyValueFactory<>("formattedCreatedAt"));
        colReceiptNumber.setCellValueFactory(new PropertyValueFactory<>("reference"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("method"));

        loadPaymentTableData();
        loadLessonTableData();
        textPaymentMethod.setItems(FXCollections.observableArrayList("Cash", "Card", "Online"));


    }

    private void loadCourse() {
        if (student == null) return;
        List<EnrollmentDTO> studentCourses = enrollmentBO.getStudentCourses(student.getId());
        courses.clear();
        courses.addAll(studentCourses);
    }

    private void loadLessonTableData() {

    }

    private void loadPaymentTableData() {
        if (student == null) return;
        tablePayment.setItems(FXCollections.observableArrayList(
                paymentBO.getStudentPayments(student.getId()).stream().map(PaymentDTO ->
                        new PaymentDTO(
                                PaymentDTO.getId(),
                                PaymentDTO.getStudentId(),
                                PaymentDTO.getAmount(),
                                PaymentDTO.getMethod(),
                                PaymentDTO.getCreatedAt(),
                                PaymentDTO.getStatus(),
                                PaymentDTO.getReference()
                        )).toList()
        ));
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


    private void showCourse(int index) {
        if (index >= 0 && index < courses.size()) {
            EnrollmentDTO course = courses.get(index);
            lblCourseId.setText(course.getCourseId());

            CourseDTO courseDTO = courseBO.searchCourse(course.getCourseId());
            lblCourseName.setText(courseDTO.getName());
            lblDuration.setText(courseDTO.getDuration());
            lblCost.setText(String.valueOf(courseDTO.getFee()));

// Calculate end date based on regDate + duration
            LocalDate startDate = course.getRegDate();
            lblJoinDate.setText(String.valueOf(startDate));

            LocalDate endDate = null;
            try {
                // Expecting duration like "12 weeks"
                String[] parts = courseDTO.getDuration().split(" ");
                int number = Integer.parseInt(parts[0]);
                String unit = parts[1].toLowerCase();

                if (unit.contains("week")) {
                    endDate = startDate.plusWeeks(number);
                } else if (unit.contains("month")) {
                    endDate = startDate.plusMonths(number);
                } else if (unit.contains("day")) {
                    endDate = startDate.plusDays(number);
                }
            } catch (Exception e) {
                System.out.println("Invalid duration format: " + courseDTO.getDuration());
            }

            if (endDate != null) {
                lblEndDate.setText(String.valueOf(endDate));
                if (LocalDate.now().isAfter(endDate)) {
                    lblStats.setText("Complete");
                } else {
                    lblStats.setText("Pending");
                }
            } else {
                lblEndDate.setText("-");
                lblStats.setText("Unknown");
            }
        } else {
            lblCourseId.setText("-");
            lblCourseName.setText("-");
            lblDuration.setText("-");
            lblCost.setText("-");
            lblJoinDate.setText("-");
            lblEndDate.setText("-");
            lblStats.setText("-");
        }
    }


    private void showPreviousCourse() {
        if (currentIndex > 0) {
            currentIndex--;
            lblPrevCount.setText(String.valueOf(currentIndex));
            lblNextCount.setText(String.valueOf(courses.size() - currentIndex - 1));
            showCourse(currentIndex);
        }
    }

    private void showNextCourse() {
        if (currentIndex < courses.size() - 1) {
            currentIndex++;
            lblPrevCount.setText(String.valueOf(currentIndex));
            lblNextCount.setText(String.valueOf(courses.size() - currentIndex - 1));
            showCourse(currentIndex);
        }
    }

}
