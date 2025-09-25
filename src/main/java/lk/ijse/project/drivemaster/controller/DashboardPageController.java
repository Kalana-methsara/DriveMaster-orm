package lk.ijse.project.drivemaster.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.StudentBO;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {

    @FXML
    private Label NoOfStudent;

    private final StudentBO studentBO =
            ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int count = studentBO.getStudentCount();
        NoOfStudent.setText(String.valueOf(count));
    }
}
