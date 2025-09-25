package lk.ijse.project.drivemaster.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LessonPageController {

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
    private TableView<?> tableView;

    @FXML
    private Label textEndTime;

    @FXML
    private ChoiceBox<?> textHours;

    @FXML
    private ChoiceBox<?> textHours1;

    @FXML
    private DatePicker textLessonDate;

    @FXML
    private ChoiceBox<?> textMinutes;

    @FXML
    private ChoiceBox<?> textMinutes1;

    @FXML
    private Label textStartTime;

    @FXML
    private ChoiceBox<?> textStatus;

    @FXML
    void onActionDelete(ActionEvent event) {

    }

    @FXML
    void onActionTimeSlot(ActionEvent event) {

    }

    @FXML
    void onActionUpdate(ActionEvent event) {

    }

    @FXML
    void onKeyHours(KeyEvent event) {

    }

    @FXML
    void onKeyMinutes(KeyEvent event) {

    }

    @FXML
    void onRefresh(MouseEvent event) {

    }

    @FXML
    void setData(MouseEvent event) {

    }

}
