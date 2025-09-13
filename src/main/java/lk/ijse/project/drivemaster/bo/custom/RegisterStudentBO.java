package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

public interface RegisterStudentBO extends SuperBO {
    void StudentRegistration(StudentDTO studentDTO, CourseDTO courseDTO, EnrollmentDTO enrollmentDTO, PaymentDTO paymentDTO);
}
