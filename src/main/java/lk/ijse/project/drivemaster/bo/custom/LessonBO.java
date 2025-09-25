package lk.ijse.project.drivemaster.bo.custom;

import javafx.scene.control.Button;
import lk.ijse.project.drivemaster.bo.SuperBO;

import java.time.LocalDate;

public interface LessonBO extends SuperBO {
    String getNextId();

    int SearchBookingLesson(LocalDate date, String inTime, String instructorId);
}
