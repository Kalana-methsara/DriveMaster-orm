package lk.ijse.project.drivemaster.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class StudentRegistrationController {

    @FXML
    private TableColumn colCourseId,colCourseFee;
    @FXML
    private TableView tableSelectedCourses;
    @FXML
    private Label lblCourseId,lblCourseName,lblCourseDuration,lblCourseFee,lblSelectedCount,lblTotal,textBalance;
    @FXML
    private DatePicker textDateOfBirth;
    @FXML
    private ChoiceBox textGender,textCity,textProvince,cmdCourses,textPaymentMethod;
    @FXML
    private TextField textFristName,textSecondName,textNic,textEmail,textContact,textAddress,textFristPayment;
    @FXML
    private Button btnSave,btnUpdate,btnDelete,btnAddCourse,btnConfirm,btnClear;

    public void onActionAddCourse(ActionEvent actionEvent) {
    }

    public void onActionConfirm(ActionEvent actionEvent) {
    }

    public void onActionClear(ActionEvent actionEvent) {
    }

    public void onActionSave(ActionEvent actionEvent) {
    }

    public void onActionUpdate(ActionEvent actionEvent) {
    }

    public void onActionDelete(ActionEvent actionEvent) {
    }
}
