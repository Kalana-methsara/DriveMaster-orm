package lk.ijse.project.drivemaster.bo.custom.impl;

import javafx.scene.control.Button;
import lk.ijse.project.drivemaster.bo.custom.LessonBO;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.InstructorDAO;
import lk.ijse.project.drivemaster.dao.custom.LessonDAO;
import lk.ijse.project.drivemaster.dao.custom.StudentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;

import java.time.LocalDate;

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

}
