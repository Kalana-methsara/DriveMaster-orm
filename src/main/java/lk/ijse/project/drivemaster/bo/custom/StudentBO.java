package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import lk.ijse.project.drivemaster.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface StudentBO extends SuperBO {
    List<StudentDTO> getAllUsers() throws Exception;

    boolean saveStudent(StudentDTO studentDTO) throws Exception;

    boolean updateStudent(StudentDTO studentDTO) throws Exception;

    boolean deleteStudent(String id) throws Exception;

    List<String> getAllIds(String id) ;

}
