package lk.ijse.project.drivemaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
    private String id;
    private String studentId;
    private String courseId;
    private LocalDate regDate;


    public EnrollmentDTO(EnrollmentDTO course) {
        this.id = course.id;
        this.courseId = course.courseId;
        this.studentId = course.studentId;
        this.regDate = course.regDate;
    }
}
