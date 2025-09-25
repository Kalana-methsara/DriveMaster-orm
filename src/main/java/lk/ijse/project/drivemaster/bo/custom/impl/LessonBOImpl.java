package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.LessonBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.LessonDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.LessonDTO;
import lk.ijse.project.drivemaster.entity.Course;
import lk.ijse.project.drivemaster.entity.Instructor;
import lk.ijse.project.drivemaster.entity.Lesson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonBOImpl implements LessonBO {

    private final LessonDAO lessonDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.LESSON);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public String getNextId() {
        String lastId = lessonDAO.getLastId();
        char tableChar = 'L';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%03d", tableChar, nextIdNumber);
        }
        return tableChar + "001";     }

    @Override
    public int SearchBookingLesson(LocalDate date, String inTime, String instructorId) {
        return lessonDAO.SearchBookingLesson(date, inTime, instructorId);
    }

    @Override
    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonDAO.getAll();
        List<LessonDTO> lessonDTOS = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDTOS.add(converter.getLessonDTO(lesson));
        }
        return lessonDTOS;
    }

    @Override
    public boolean saveLesson(LessonDTO dto) throws Exception {
        Optional<Lesson> optionalLesson = lessonDAO.findById(dto.getId());
        if (optionalLesson.isPresent()) {
            throw new DuplicateException("Duplicate Lesson id");
        }
        Lesson lesson = converter.getLesson(dto);
        return lessonDAO.save(lesson);      }

    @Override
    public boolean updateLesson(LessonDTO dto) throws Exception {
        Optional<Lesson> optionalLesson = lessonDAO.findById(dto.getId());
        if (!optionalLesson.isPresent()) {
            throw new RuntimeException("Lesson not found");
        }
        Lesson lesson = converter.getLesson(dto);
        return lessonDAO.update(lesson);     }

    @Override
    public boolean deleteLesson(String id) throws Exception {
        Optional<Lesson> optionalLesson = lessonDAO.findById(id);
        if (optionalLesson.isEmpty()) {
            throw new NotFoundException("Lesson not found..!");
        }
        return lessonDAO.delete(id);     }

    @Override
    public List<LessonDTO> getAllLessonsById(String id) {
        List<Lesson> lessons = lessonDAO.getAllLessonsById(id);
        List<LessonDTO> lessonDTOS = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDTOS.add(converter.getLessonDTO(lesson));
        }
        return lessonDTOS;
    }

    @Override
    public boolean updateLessonStatus(String id, String choice) {
        return lessonDAO.updateLessonStatus(id,choice);
    }

}
