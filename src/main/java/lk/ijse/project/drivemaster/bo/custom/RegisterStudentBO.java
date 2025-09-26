package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.util.List;

public interface RegisterStudentBO extends SuperBO {
    boolean StudentRegistration(StudentDTO student, List<EnrollmentDTO> enrollments, PaymentDTO payment);
    boolean UpdateRegistration(StudentDTO student, List<EnrollmentDTO> enrollments, PaymentDTO payment);
}
