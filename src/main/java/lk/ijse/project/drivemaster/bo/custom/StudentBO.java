package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface StudentBO extends SuperBO {
    List<StudentDTO> getAllStudent() throws Exception;

    boolean saveStudent(StudentDTO studentDTO) throws Exception;

    boolean updateStudent(StudentDTO studentDTO) throws Exception;

    boolean deleteStudent(String id) throws Exception;

    List<String> getAllIds(String id) ;

     StudentDTO getStudentById(Long id);

    Long getLastId();
}
