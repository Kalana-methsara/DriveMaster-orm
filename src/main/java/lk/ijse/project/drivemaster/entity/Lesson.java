package lk.ijse.project.drivemaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lk.ijse.project.drivemaster.enums.LessonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="lessons")
public class Lesson {
    @Id
    private String id; // L001

    @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student;
    @ManyToOne(optional=false) @JoinColumn(name="course_id") private Course course;
    @ManyToOne(optional=false) @JoinColumn(name="instructor_id") private Instructor instructor;

    @NotNull private String startTime;
    @NotNull
    private String endTime;
    @NotNull
    private LocalDate lessonDate;

    @Enumerated(EnumType.STRING) private LessonStatus status = LessonStatus.BOOKED;
}

