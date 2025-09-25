package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.LessonDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface LessonBO extends SuperBO {
    String getNextId();

    int SearchBookingLesson(LocalDate date, String inTime, String instructorId);

    List<LessonDTO> getAllLessons();
    boolean saveLesson(LessonDTO lessonDTO) throws Exception;
    boolean updateLesson(LessonDTO lessonDTO) throws Exception;
    boolean deleteLesson(String id) throws Exception;

    List<LessonDTO> getAllLessonsById(String id);

    boolean updateLessonStatus(String id, String choice);
}
