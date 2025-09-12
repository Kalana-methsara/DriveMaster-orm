package lk.ijse.project.drivemaster.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.StudentBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.CourseDetailsDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EnrolleeDetailsController implements Initializable {

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
    private TableColumn<?, ?> colAction;

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
    private ChoiceBox<?> textPaymentMethod;

    private final List<CourseDetailsDTO> courses = new ArrayList<>();
    private int currentIndex = 0;


    @FXML
    void onActionClear(ActionEvent event) {

    }

    @FXML
    void onActionConfirm(ActionEvent event) {

    }

    @FXML
    void onActionPayment(ActionEvent event) {

    }

    @FXML
    void onActionPrint(ActionEvent event) {

    }

    @FXML
    void onActionUpdateStatus(ActionEvent event) {

    }

    @FXML
    void onKeyBalance(KeyEvent event) {

    }
    private final StudentBO studentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courses.add(new CourseDetailsDTO("C1001", "Basic Learner Program", "12 weeks",
                new BigDecimal("50000"), LocalDate.parse("2024-01-10"), "pending"));
        courses.add(new CourseDetailsDTO("C1002", "Advanced Defensive Driving", "8 weeks",
                new BigDecimal("65000"), LocalDate.parse("2024-02-05"), "complete"));
        courses.add(new CourseDetailsDTO("C1003", "Motorcycle License Training", "16 weeks",
                new BigDecimal("75000"), LocalDate.parse("2024-03-15"), "pending"));

        setStudentData();

        showCourse(currentIndex);
        lblNextCount.setText(String.valueOf(courses.size()-1));
        lblPrevCount.setText(String.valueOf(currentIndex));

        btnPrevCourse.setOnAction(e -> showPreviousCourse());
        btnNextCourse.setOnAction(e -> showNextCourse());
    }

    private void setStudentData() {
        StudentSearchController studentSearch = new StudentSearchController();
       long id = studentSearch.getStudentId();
       StudentDTO student = studentBO.getStudentById(id);
       lblStudentName.setText(student.getLastName()+" "+student.getFirstName());
       lblAddress.setText(student.getAddress());



    }


    private void showCourse(int index) {
        if (index >= 0 && index < courses.size()) {
            CourseDetailsDTO course = courses.get(index);
            lblCourseId.setText(course.getId());
            lblCourseName.setText(course.getName());
            lblDuration.setText(course.getDuration());
            lblCost.setText(String.valueOf(course.getFee()));
            lblEndDate.setText(String.valueOf(course.getEndDate()));
            lblStats.setText(course.getStatus());
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
