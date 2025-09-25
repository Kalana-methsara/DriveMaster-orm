package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Lesson;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LessonDAO extends CrudDAO<Lesson> {
    int SearchBookingLesson(LocalDate date, String inTime, String instructorId);

    String getLastId();

    Optional<Lesson> findById(String id);

    List<Lesson> getAllLessonsById(String id);

    boolean updateLessonStatus(String id, String choice);
}
