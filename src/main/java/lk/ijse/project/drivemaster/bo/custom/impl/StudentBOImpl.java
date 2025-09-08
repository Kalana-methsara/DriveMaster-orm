package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.StudentBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.StudentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import lk.ijse.project.drivemaster.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.STUDENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<StudentDTO> getAllStudent() throws SQLException, ClassNotFoundException {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(converter.getStudentDTO(student));
        }
        return studentDTOS;
    }

    @Override
    public void saveCustomer(StudentDTO dto) throws DuplicateException, Exception {

    }

    @Override
    public void updateCustomer(StudentDTO dto) {

    }

    @Override
    public boolean deleteCustomer(String id) throws InUseException, Exception {
        return false;
    }

    @Override
    public List<String> getAllIds(String id) {
        return List.of();
    }
}
