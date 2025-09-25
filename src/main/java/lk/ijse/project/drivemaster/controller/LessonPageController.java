package lk.ijse.project.drivemaster.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.custom.LessonBO;
import lk.ijse.project.drivemaster.bo.custom.StudentBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;
import lk.ijse.project.drivemaster.dto.LessonDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class LessonPageController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colDate;

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
    private Label lblStudentId;

    @FXML
    private Label lblStudentName;

    @FXML
    private TableView<LessonDTO> tableView;

    @FXML
    private Label textEndTime;

    @FXML
    private ChoiceBox<String> textHours;

    @FXML
    private ChoiceBox<String> textHours1;

    @FXML
    private DatePicker textLessonDate;

    @FXML
    private ChoiceBox<String> textMinutes;

    @FXML
    private ChoiceBox<String> textMinutes1;

    @FXML
    private Label textStartTime;

    @FXML
    private ChoiceBox<String> textStatus;

    private final LessonBO lessonBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.LESSON);
    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);
    private final InstructorBO instructorBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.INSTRUCTOR);
    private final StudentBO studentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);


    @FXML
    void onActionDelete(ActionEvent event) {
        var selectedLesson = tableView.getSelectionModel().getSelectedItem();
        if (selectedLesson == null) {
            showAlert(Alert.AlertType.WARNING, "No Lesson Selected", "Please select a lesson before proceeding.");
            return;
        }
        boolean confirmed = showConfirmation("Confirm Delete", "Are you sure you want to delete this lesson?");
        if (confirmed) {
            try {
                String lessonId = lblId.getText();
                boolean isDeleted = lessonBO.deleteLesson(lessonId);

                if (isDeleted) {
                    clearLessonFields();
                    loadLessonTable();
//                    showAlert(Alert.AlertType.INFORMATION, "Success", "lesson deleted successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "No lesson found with this ID.");
                }
            } catch (InUseException e) {
                showAlert(Alert.AlertType.ERROR, "In Use", "This lesson is currently in use and cannot be deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete lesson. Please try again!");
            }
        }
    }


    @FXML
    void onActionUpdate(ActionEvent event) {
        var selectedLesson = tableView.getSelectionModel().getSelectedItem();
        if (selectedLesson == null) {
            showAlert(Alert.AlertType.WARNING, "No Lesson Selected", "Please select a lesson before proceeding.");
            return;
        }
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }
        String startTime = textStartTime.getText() != null ? textStartTime.getText().trim() : "";
        String endTime = textEndTime.getText() != null ? textEndTime.getText().trim() : "";
        String status = textStatus.getValue() != null ? textStatus.getValue().toString().trim() : "";
        LocalDate lessonDate = textLessonDate.getValue();
        LessonDTO lessonDTO = new LessonDTO(selectedLesson.getId(),selectedLesson.getStudentId(),selectedLesson.getCourseId(),selectedLesson.getInstructorId(),startTime, endTime, lessonDate, status);
        try {
            lessonBO.updateLesson(lessonDTO);
            clearLessonFields();
            loadLessonTable();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Lesson updated successfully!");
        } catch (NotFoundException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lesson Not Found", "No lesson found with this ID!");
        } catch (DuplicateException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Duplicate Lesson", "A lesson with this name or ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Update", "Failed to update lesson. Please try again!");
        }
    }

    private boolean validateInputs() {
        String startTime = textStartTime.getText() != null ? textStartTime.getText().trim() : "";
        String endTime = textEndTime.getText() != null ? textEndTime.getText().trim() : "";
        String status = textStatus.getValue() != null ? textStatus.getValue().toString().trim() : "";
        LocalDate lessonDate = textLessonDate.getValue();

        if (startTime.isEmpty()) {
            System.out.println("Start time is required");
            return false;
        }
        if (endTime.isEmpty()) {
            System.out.println("End time is required");
            return false;
        }
        if (status.isEmpty()) {
            System.out.println("Status is required");
            return false;
        }
        if (lessonDate == null) {
            System.out.println("Lesson date is required");
            return false;
        }

        return true;
    }


    @FXML
    void onKeyHours(KeyEvent event) {
        textMinutes.requestFocus();
    }

    @FXML
    void onKeyMinutes(KeyEvent event) {
        String hours = textHours.getValue();
        String minutes = textMinutes.getValue();
        textStartTime.setText(hours + ":" + minutes);
        textHours.setValue(null);
        textMinutes.setValue(null);
    }

    @FXML
    void onKeyHours1(KeyEvent event) {
        textMinutes1.requestFocus();
    }

    @FXML
    void onKeyMinutes1(KeyEvent event) {
        String hours = textHours1.getValue();
        String minutes = textMinutes1.getValue();
        textEndTime.setText(hours + ":" + minutes);
        textHours1.setValue(null);
        textMinutes1.setValue(null);
    }

    @FXML
    void onRefresh(MouseEvent event) {
        clearLessonFields();
    }

    @FXML
    void setData(MouseEvent event) {
        LessonDTO selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(selected.getId());
            lblStudentId.setText(selected.getStudentId());
            StudentDTO studentDTO = studentBO.getStudentById(selected.getStudentId());
            if (studentDTO != null) {
                lblStudentId.setText(studentDTO.getId());
                lblStudentName.setText(studentDTO.getFirstName() + " " + studentDTO.getLastName());
            }
            CourseDTO courseDTO = courseBO.searchCourse(selected.getCourseId());
            if (courseDTO != null) {
                lblCourseId.setText(courseDTO.getId());
                lblCourseName.setText(courseDTO.getName());
            }
            InstructorDTO instructorDTO = instructorBO.searchInstructor(selected.getInstructorId());
            if (instructorDTO != null) {
                lblInstructorId.setText(instructorDTO.getId());
                lblInstructorName.setText(instructorDTO.getName());
            }
            textStartTime.setText(selected.getStartTime());
            textEndTime.setText(selected.getEndTime());
            textLessonDate.setValue(selected.getLessonDate());
            textStatus.setValue(selected.getStatus());
        }
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


        for (int i = 0; i <= 24; i++) {
            textHours.getItems().add(String.format("%02d", i));
            textHours1.getItems().add(String.format("%02d", i));
        }

        // Add 00 to 59 in minuteBox
        for (int i = 0; i <= 59; i++) {
            textMinutes.getItems().add(String.format("%02d", i));
            textMinutes1.getItems().add(String.format("%02d", i));
        }
        textStatus.setItems(FXCollections.observableArrayList("BOOKED", "COMPLETED", "CANCELED"));
        clearLessonFields();
    }

    private void clearLessonFields() {
        loadNextId();
        loadLessonTable();
        textStartTime.setText("");
        textEndTime.setText("");
        tableView.getSelectionModel().clearSelection();
        textLessonDate.setValue(LocalDate.now());
        textStatus.setValue("");
        lblStudentId.setText("");
        lblCourseId.setText("");
        lblInstructorId.setText("");
        lblStudentName.setText("");
        lblCourseName.setText("");
        lblInstructorName.setText("");

    }

    private void loadLessonTable() {
        tableView.setItems(FXCollections.observableArrayList(
                lessonBO.getAllLessons().stream().map(LessonDTO ->
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

    private void loadNextId() {
        String nextId = lessonBO.getNextId();
        lblId.setText(nextId);
    }
    private void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().setStyle("-fx-border-color: linear-gradient(#7b4397, #dc2430); -fx-border-width: 3px;");
        alert.showAndWait();
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

}
