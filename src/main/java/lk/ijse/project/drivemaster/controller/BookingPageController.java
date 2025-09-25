package lk.ijse.project.drivemaster.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.custom.EnrollmentBO;
import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.custom.LessonBO;
import lk.ijse.project.drivemaster.dto.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private TableColumn<?, ?>colDate;

    @FXML
    private TableView<LessonDTO> tableView;

    @FXML
    private DatePicker textLessonDate;



    private final LessonBO lessonBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.LESSON);
    private final EnrollmentBO enrollmentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.ENROLLMENT);
    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);
    private final InstructorBO instructorBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.INSTRUCTOR);


    private StudentDTO student;

    // currently selected time slot string (e.g. "08.00")
    private String selectedTimeSlot;

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
        handleTimeButton("10.30", btn1030);
    }

    @FXML
    void onAction11(ActionEvent event) {
        handleTimeButton("11.00", btn11);
    }

    @FXML
    void onAction1130(ActionEvent event) {
        handleTimeButton("11.30", btn1130);
    }

    @FXML
    void onAction12(ActionEvent event) {
        handleTimeButton("12.00", btn12);
    }

    @FXML
    void onAction1330(ActionEvent event) {
        handleTimeButton("13.30", btn1330);
    }

    @FXML
    void onAction14(ActionEvent event) {
        handleTimeButton("14.00", btn14);
    }

    @FXML
    void onAction1430(ActionEvent event) {
        handleTimeButton("14.30", btn1430);
    }

    @FXML
    void onAction15(ActionEvent event) {
        handleTimeButton("15.30", btn15);
    }

    @FXML
    void onAction16(ActionEvent event) {
        handleTimeButton("16.00", btn16);
    }

    @FXML
    void onAction1630(ActionEvent event) {
        handleTimeButton("16.30", btn1630);
    }


    @FXML
    void onActionTheory(ActionEvent event) {
        handleTimeButton("08.00", btn8);

    }


    @FXML
    void onRefresh(MouseEvent event) {
        clearLessonFields();
    }

    @FXML
    void setData(MouseEvent event) {
        LessonDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        lblId.setText(selected.getId());
        lblCourseId.setText(selected.getCourseId());
        lblCourseName.setText(courseBO.searchCourse(selected.getCourseId()).getName());
        lblInstructorId.setText(selected.getInstructorId());
        lblInstructorName.setText(instructorBO.searchInstructor(selected.getInstructorId()).getName());
        textLessonDate.setValue(selected.getLessonDate());
        selectedTimeSlot = selected.getStartTime();
    }


    private void loadTableData() {
        if (student == null) return;

        tableView.setItems(FXCollections.observableArrayList(
                lessonBO.getAllLessonsById(student.getId()).stream().map(LessonDTO ->
                        new LessonDTO(
                                LessonDTO.getId(),
                                LessonDTO.getStudentId(),
                                LessonDTO.getCourseId(),
                                LessonDTO.getInstructorId(),
                                LessonDTO.getStartTime(),
                                LessonDTO.getEndTime(),
                                LessonDTO.getLessonDate(),
                                LessonDTO.getStatus()
                        )).toList()
        ));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colLessonId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

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
            if (btn == null) continue;
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
            if (btn == null) continue;
            String time = entry.getValue();

            int count = 0;
            try {
                count = lessonBO.SearchBookingLesson(date, time, instructorId);
            } catch (Exception ex) {
                // if underlying BO method isn't available or fails, we silently treat as 0
                count = 0;
            }
            int limit = time.equals("08.00") ? 5 : 1;

            if (count >= limit) {
                btn.setStyle("-fx-background-color: #FFE5E5; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-border-color: #e74c3c; -fx-border-radius: 6; -fx-background-radius: 6;");
                btn.setDisable(true);

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
        selectedTimeSlot = null;
        btnColor();
    }

    private void loadNextId() {
        String nextId = null;
        try {
            nextId = lessonBO.getNextId();
        } catch (Exception ex) {
            nextId = "L-ERR";
        }
        lblId.setText(nextId);
    }

    public void onActionUpdateStatus(ActionEvent actionEvent) {
        LessonDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No selection", "Please select a lesson from the table first.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(selected.getStatus(), "BOOKED", "COMPLETED", "CANCELED");
        dialog.setTitle("Update Status");
        dialog.setHeaderText("Update lesson status");
        dialog.setContentText("Choose status:");
        dialog.initStyle(StageStyle.UNDECORATED);

        dialog.showAndWait().ifPresent(choice -> {
            try {
                boolean ok = lessonBO.updateLessonStatus(selected.getId(), choice);
                if (ok) {
//                    showAlert(Alert.AlertType.INFORMATION, "Status updated", "Lesson status updated to " + choice);
                    loadTableData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Update failed", "Could not update status. Please try again.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update status: " + ex.getMessage());
            }
        });
    }

    public void onActionLesson(ActionEvent actionEvent) {
        // create/save a lesson booking using selected fields
        if (student == null) {
            showAlert(Alert.AlertType.WARNING, "No student", "No student selected for booking.");
            return;
        }
        if (lblInstructorId.getText() == null || lblInstructorId.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No instructor", "Please select an instructor first.");
            return;
        }
        if (cmdCourse.getValue() == null || cmdCourse.getValue().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No course", "Please select a course first.");
            return;
        }
        if (selectedTimeSlot == null || selectedTimeSlot.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No time slot", "Please pick a time slot.");
            return;
        }

        String id = lblId.getText();
        String studentId = student.getId();
        String courseId = cmdCourse.getValue();
        String instructorId = lblInstructorId.getText();
        String startTime = selectedTimeSlot;
        String endTime = computeEndTime(startTime);
        LocalDate lessonDate = textLessonDate.getValue();
        String status = "BOOKED";

        LessonDTO lesson = new LessonDTO(id, studentId, courseId, instructorId, startTime, endTime, lessonDate, status);

        try {
            boolean saved = lessonBO.saveLesson(lesson);
            if (saved) {
                showAlert(Alert.AlertType.INFORMATION, "Booked", "Lesson booked successfully.");
                clearLessonFields();
                loadTableData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Booking failed. Try again.");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not create booking: " + ex.getMessage());
        }

    }

    public void onActionPrint(ActionEvent actionEvent) {
        // simple placeholder: the real implementation would format and send to a printer or export to PDF
        LessonDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No selection", "Select a lesson to print.");
            return;
        }

        String content = String.format("Lesson ID: %s\nStudent: %s\nCourse: %s\nInstructor: %s\nDate: %s\nTime: %s - %s\nStatus: %s",
                selected.getId(), selected.getStudentId(), selected.getCourseId(), selected.getInstructorId(), selected.getLessonDate(), selected.getStartTime(), selected.getEndTime(), selected.getStatus());

        showAlert(Alert.AlertType.INFORMATION, "Print Preview", content);
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

    // --- Helper methods ---

    private void handleTimeButton(String timeSlot, Button btn) {
        // highlight chosen button visually and remember the slot
        clearTimeSelectionStyles();
        if (btn != null) btn.setStyle("-fx-background-color: #6fdc6f; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 6; -fx-background-radius: 6;");
        selectedTimeSlot = timeSlot;
    }

    private void clearTimeSelectionStyles() {
        Button[] buttons = {btn8, btn1030, btn11, btn1130, btn12, btn1330, btn14, btn1430, btn15, btn16, btn1630};
        for (Button b : buttons) {
            if (b == null) continue;
            b.setStyle("-fx-background-color: #98ff98; -fx-text-fill: #228b22; -fx-font-weight: bold; -fx-border-color: #228b22; -fx-border-radius: 6; -fx-background-radius: 6;");
        }
    }

    private String computeEndTime(String start) {
        // compute a simple end time mapping for UI. This matches how times are presented on the buttons.
        return switch (start) {
            case "08.00" -> "10.00";
            case "10.30" -> "11.00";
            case "11.00" -> "11.30";
            case "11.30" -> "12.00";
            case "12.00" -> "12.30";
            case "13.30" -> "14.00";
            case "14.00" -> "14.30";
            case "14.30" -> "15.00";
            case "15.30" -> "16.00";
            case "16.00" -> "16.30";
            case "16.30" -> "17.00";
            default -> start;
        };
    }

}
