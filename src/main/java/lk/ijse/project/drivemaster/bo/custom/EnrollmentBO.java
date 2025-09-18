package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;

public interface EnrollmentBO extends SuperBO {
    boolean saveCourse(EnrollmentDTO enrollmentDTO) throws Exception;
    String getNextId();

}
