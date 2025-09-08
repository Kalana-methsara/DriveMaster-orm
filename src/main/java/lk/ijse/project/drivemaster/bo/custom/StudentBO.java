package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.InUseException;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public interface StudentBO extends SuperBO {
    List<StudentDTO> getAllStudent() throws SQLException, ClassNotFoundException;

    void saveCustomer(StudentDTO dto) throws DuplicateException, Exception;

    void updateCustomer(StudentDTO dto) ;

    boolean deleteCustomer(String id) throws InUseException, Exception;

    List<String> getAllIds(String id) ;

}
