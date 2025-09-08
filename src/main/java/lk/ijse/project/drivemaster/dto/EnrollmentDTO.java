package lk.ijse.project.drivemaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO { private Long id;
    private Long studentId;
    private String courseId;
    private LocalDate regDate;
    private BigDecimal upfrontPaid;

    private CourseDTO course;
}
