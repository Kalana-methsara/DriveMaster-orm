package lk.ijse.project.drivemaster.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lk.ijse.project.drivemaster.bo.BOFactoryImpl;
import lk.ijse.project.drivemaster.bo.BOType;
import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.custom.PaymentBO;
import lk.ijse.project.drivemaster.bo.custom.StudentBO;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {

    @FXML
    private Label lblTotalInstructors;
    @FXML
    private Label lblActiveLessons;
    @FXML
    private Label lblMonthlyIncome;
    @FXML
    private Label lblTotalStudents;

    private final StudentBO studentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.STUDENT);
    private final CourseBO courseBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.COURSE);
    private final InstructorBO instructorBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.INSTRUCTOR);
    private final PaymentBO paymentBO = ((BOFactoryImpl) BOFactoryImpl.getInstance()).getBO(BOType.PAYMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int studentCount = studentBO.getStudentCount();
        lblTotalStudents.setText(String.valueOf(studentCount));

        int instructorCount = instructorBO.getInstructorCount();
        lblTotalInstructors.setText(String.valueOf(instructorCount));

        int courseCount = courseBO.getStudentCount();
        lblActiveLessons.setText(String.valueOf(courseCount));

       double monthlyIncome =  paymentBO.getMonthlyIncome();
        lblMonthlyIncome.setText(String.valueOf(monthlyIncome));
    }

}
