package lk.ijse.project.drivemaster.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.custom.EnrollmentBO;
import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.custom.LessonBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BookingPageController implements Initializable {

    @FXML
    private Button btn1030;

    @FXML
    private Button btn11;

    @FXML
    private Button btn1130;

    @FXML
    private Button btn12;

    @FXML
    private Button btn1330;

    @FXML
    private Button btn14;

    @FXML
    private Button btn1430;

    @FXML
    private Button btn15;

    @FXML
    private Button btn16;

    @FXML
    private Button btn1630;

    @FXML
    private Button btn8;


    @FXML
    private ComboBox<String> cmdCourse;

    @FXML
    private ComboBox<String> cmdInstructor;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colEndTime;

    @FXML
    private TableColumn<?, ?> colInstructorId;

    @FXML
    private TableColumn<?, ?> colLessonId;

    @FXML
    private TableColumn<?, ?> colStartTime;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private Label lblCourseId;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblId;

    @FXML
    private Label lblInstructorId;

    @FXML
    private Label lblInstructorName;


    @FXML
    private Label lblStudentName;

    @FXML
    private TableView<?> tableView;

    @FXML
    private DatePicker textLessonDate;



    private final LessonBO lessonBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.LESSON);
    private final EnrollmentBO enrollmentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.ENROLLMENT);
    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);
    private final InstructorBO instructorBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.INSTRUCTOR);


    private StudentDTO student;
    public void setStudent(StudentDTO student) {
        this.student = student;
        setStudentData();
        loadTableData();

    }

    private void setStudentData() {
        if (student == null) return;

        lblStudentName.setText(student.getFirstName() + " " + student.getLastName());

        List<EnrollmentDTO> studentCourses = enrollmentBO.getStudentCourses(student.getId());

        if (studentCourses != null && !studentCourses.isEmpty()) {
            cmdCourse.getItems().clear();

            for (EnrollmentDTO course : studentCourses) {
                String display = course.getCourseId();
                cmdCourse.getItems().add(display);
            }

        }
    }



    @FXML
    void onAction1030(ActionEvent event) {

    }

    @FXML
    void onAction11(ActionEvent event) {

    }

    @FXML
    void onAction1130(ActionEvent event) {

    }

    @FXML
    void onAction12(ActionEvent event) {

    }

    @FXML
    void onAction1330(ActionEvent event) {

    }

    @FXML
    void onAction14(ActionEvent event) {

    }

    @FXML
    void onAction1430(ActionEvent event) {

    }

    @FXML
    void onAction15(ActionEvent event) {

    }

    @FXML
    void onAction16(ActionEvent event) {

    }

    @FXML
    void onAction1630(ActionEvent event) {

    }


    @FXML
    void onActionTheory(ActionEvent event) {

    }

    @FXML
    void onActionTimeSlot(ActionEvent event) {

    }

    @FXML
    void onRefresh(MouseEvent event) {
        clearLessonFields();
    }

    @FXML
    void setData(MouseEvent event) {

    }


    private void loadTableData() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearLessonFields();
        btnColor();
    }
    private void addZoomEffect(Button btn) {
        btn.setOnMouseEntered(e -> {
            btn.setScaleX(1.2);
            btn.setScaleY(1.2);
        });
        btn.setOnMouseExited(e -> {
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        });
    }

    private void btnColor() {
        Button[] buttons = {btn8, btn1030, btn11, btn1130, btn12, btn1330, btn14, btn1430, btn15, btn16, btn1630};
        for (Button btn : buttons) {
            btn.setStyle("-fx-background-color: #98ff98; -fx-text-fill: #228b22; -fx-font-weight: bold; -fx-border-color: #228b22; -fx-border-radius: 6; -fx-background-radius: 6;");
            btn.setDisable(false);
            addZoomEffect(btn);
        }

        LocalDate date = textLessonDate.getValue();
        String instructorId = lblInstructorId.getText();
        if (instructorId == null || instructorId.isEmpty()) {
            return;
        }

        Map<Button, String> timeSlots = new LinkedHashMap<>();
        timeSlots.put(btn8,    "08.00");
        timeSlots.put(btn1030, "10.30");
        timeSlots.put(btn11,   "11.00");
        timeSlots.put(btn1130, "11.30");
        timeSlots.put(btn12,   "12.00");
        timeSlots.put(btn1330, "13.30");
        timeSlots.put(btn14,   "14.00");
        timeSlots.put(btn1430, "14.30");
        timeSlots.put(btn15,   "15.30");
        timeSlots.put(btn16,   "16.00");
        timeSlots.put(btn1630, "16.30");

        for (Map.Entry<Button, String> entry : timeSlots.entrySet()) {
            Button btn = entry.getKey();
            String time = entry.getValue();

            int count = lessonBO.SearchBookingLesson(date, time, instructorId);
            int limit = time.equals("08.00") ? 5 : 1;

            if (count >= limit) {
                btn.setStyle("-fx-background-color: #FFE5E5; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-border-color: #e74c3c; -fx-border-radius: 6; -fx-background-radius: 6;");
                btn.setDisable(true);
                showAlert(Alert.AlertType.WARNING, "All instructor slots booked for " + time, "Booking");
            }
        }
    }


    private void clearLessonFields() {
        loadTableData();
        loadNextId();
        textLessonDate.setValue(LocalDate.now());
        lblCourseId.setText("");
        lblCourseName.setText("");
        lblInstructorId.setText("");
        lblInstructorName.setText("");
        cmdCourse.setValue(null);
        cmdInstructor.setValue(null);
        tableView.getSelectionModel().clearSelection();
        btnColor();
    }

    private void loadNextId() {
        String nextId = lessonBO.getNextId();
        lblId.setText(nextId);
    }

    public void onActionUpdateStatus(ActionEvent actionEvent) {
    }

    public void onActionLesson(ActionEvent actionEvent) {
    }

    public void onActionPrint(ActionEvent actionEvent) {
    }

    public void onCourse(ActionEvent keyEvent) {
        String id = cmdCourse.getValue();
        if(id == null || id.isEmpty()) {
            return;
        }
        CourseDTO courseDTO = courseBO.searchCourse(id);
        if (courseDTO != null) {
            lblCourseId.setText(courseDTO.getId());
            lblCourseName.setText(courseDTO.getName());

            cmdInstructor.getItems().clear();

            switch (courseDTO.getId()) {
                case "C1001" -> cmdInstructor.getItems().addAll("I001", "I004");
                case "C1002" -> cmdInstructor.getItems().addAll("I002", "I004");
                case "C1003" -> cmdInstructor.getItems().addAll("I001", "I003");
                case "C1004" -> cmdInstructor.getItems().addAll("I003", "I004");
                case "C1005" -> cmdInstructor.getItems().addAll("I002", "I003");
            }

        }
    }

    public void onInstructor(ActionEvent keyEvent) {
        String id = cmdInstructor.getValue();
        if(id == null || id.isEmpty()) {
            return;
        }
        InstructorDTO instructorDTO = instructorBO.searchInstructor(id);
        if (instructorDTO != null) {
            lblInstructorId.setText(instructorDTO.getId());
            lblInstructorName.setText(instructorDTO.getName());
            btnColor();
        }

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
