package lk.ijse.project.drivemaster.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CourseDTO, Double> colCost;

    @FXML
    private TableColumn<CourseDTO, String> colCourseID;

    @FXML
    private TableColumn<CourseDTO, String> colCourseName;

    @FXML
    private TableColumn<CourseDTO, String> colDuration;

    @FXML
    private TableView<CourseDTO> tblCourse;

    @FXML
    private TextField textCourseFee;

    @FXML
    private Label textCourseId;

    @FXML
    private TextField textCourseName;

    @FXML
    private TextField textDuration;

    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);


    @FXML
    void onActionDelete(ActionEvent event) {
        boolean confirmed = showConfirmation("Confirm Delete", "Are you sure you want to delete this course?");
        if (confirmed) {
            try {
                String courseId = textCourseId.getText();
                boolean isDeleted = courseBO.deleteCourse(courseId);

                if (isDeleted) {
                    clearCourseFields();
                    loadTableData();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Course deleted successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "No course found with this ID.");
                }
            } catch (InUseException e) {
                showAlert(Alert.AlertType.ERROR, "In Use", "This course is currently in use and cannot be deleted.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete course. Please try again!");
            }
        }
    }


    @FXML
    void onActionSave(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }
        String id = textCourseId.getText() != null ? textCourseId.getText().trim() : "";
        String name = textCourseName.getText() != null ? textCourseName.getText().trim() : "";
        String duration = textDuration.getText() != null ? textDuration.getText().trim() : "";
        BigDecimal feeText = (textCourseFee.getText() != null && !textCourseFee.getText().trim().isEmpty())
                ? new BigDecimal(textCourseFee.getText().trim())
                : BigDecimal.ZERO;

        CourseDTO courseDTO = new CourseDTO(id, name, duration, feeText);

        try {
            courseBO.saveCourse(courseDTO);
            clearCourseFields();
            loadTableData();
//            showAlert(Alert.AlertType.INFORMATION, "Success", "Course saved successfully!");
        } catch (DuplicateException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Duplicate Course", "A course with this ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Save", "Failed to save course. Please try again!");
        }
    }

    @FXML
    void onActionUpdate(ActionEvent event) {
        if (!validateInputs()) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please check your input fields.");
            return;
        }
        String id = textCourseId.getText() != null ? textCourseId.getText().trim() : "";
        String name = textCourseName.getText() != null ? textCourseName.getText().trim() : "";
        String duration = textDuration.getText() != null ? textDuration.getText().trim() : "";
        BigDecimal feeText = (textCourseFee.getText() != null && !textCourseFee.getText().trim().isEmpty())
                ? new BigDecimal(textCourseFee.getText().trim())
                : BigDecimal.ZERO;

        CourseDTO courseDTO = new CourseDTO(id, name, duration, feeText);

        try {
            courseBO.updateCourse(courseDTO);
            clearCourseFields();
            loadTableData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully!");
        } catch (NotFoundException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Course Not Found", "No course found with this ID!");
        } catch (DuplicateException e) {
//            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Duplicate Course", "A course with this name or ID already exists!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed Update", "Failed to update course. Please try again!");
        }
    }


    @FXML
    void onRefresh(MouseEvent event) {
        clearCourseFields();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCourseID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("fee"));

        try {
            loadTableData();
            clearCourseFields();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clearCourseFields() {
        loadNextId();
        textCourseName.clear();
        textCourseFee.clear();
        textDuration.clear();
    }

    private void loadNextId() {
        String nextId = courseBO.getNextId();
        textCourseId.setText(nextId);
    }

    private void loadTableData() throws Exception {
        tblCourse.setItems(FXCollections.observableArrayList(
                courseBO.getAllCourses().stream().map(CourseDTO ->
                        new CourseDTO(
                                CourseDTO.getId(),
                                CourseDTO.getName(),
                                CourseDTO.getDuration(),
                                CourseDTO.getFee()
                        )).toList()
        ));

    }

    public void setData(MouseEvent mouseEvent) {
        CourseDTO selected = tblCourse.getSelectionModel().getSelectedItem();
        if (selected != null) {
            textCourseId.setText(selected.getId());
            textCourseName.setText(selected.getName());
            textDuration.setText(selected.getDuration());
            textCourseFee.setText(String.valueOf(selected.getFee()));
        }
    }
    private boolean validateInputs() {
        String name = textCourseName.getText() != null ? textCourseName.getText().trim() : "";
        String duration = textDuration.getText() != null ? textDuration.getText().trim() : "";
        String feeText = textCourseFee.getText() != null ? textCourseFee.getText().trim() : "";

        boolean isValidName = !name.isEmpty();
        boolean isValidDuration = !duration.isEmpty();
        boolean isValidFee = false;

        try {
            double fee = Double.parseDouble(feeText);
            isValidFee = fee > 0;
        } catch (NumberFormatException e) {
            isValidFee = false;
        }

        if (!isValidName) showErrorWithTimeout(textCourseName);
        if (!isValidDuration) showErrorWithTimeout(textDuration);
        if (!isValidFee) showErrorWithTimeout(textCourseFee);

        return  isValidName && isValidDuration && isValidFee;
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
}
