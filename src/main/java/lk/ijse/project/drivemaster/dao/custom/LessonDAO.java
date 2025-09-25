package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Lesson;

import java.time.LocalDate;

public interface LessonDAO extends CrudDAO<Lesson> {
    int SearchBookingLesson(LocalDate date, String inTime, String instructorId);

    String getLastId();
}
