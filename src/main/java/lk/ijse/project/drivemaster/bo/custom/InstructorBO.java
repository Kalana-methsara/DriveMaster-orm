package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;

import java.util.List;

public interface InstructorBO extends SuperBO {
    List<InstructorDTO> getAllInstructors() throws Exception;

    boolean saveInstructor(InstructorDTO instructorDTO) throws Exception;

    boolean updateInstructor(InstructorDTO instructorDTO) throws Exception;

    boolean deleteInstructor(String id) throws Exception;

    List<String> getAllIds(String id) ;

    String getNextId();

    InstructorDTO searchInstructor(String id);
}
