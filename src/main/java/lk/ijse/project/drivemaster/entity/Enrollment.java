package lk.ijse.project.drivemaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="enrollments",
        uniqueConstraints=@UniqueConstraint(columnNames={"student_id","course_id"}))
public class Enrollment {
    @Id
    private String id; // E001

    @ManyToOne(optional=false) @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne(optional=false) @JoinColumn(name="course_id")
    private Course course;

    @NotNull
    private LocalDate regDate;
    @NotNull @Column(precision=10, scale=2) private BigDecimal upfrontPaid;
}

