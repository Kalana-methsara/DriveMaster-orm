package lk.ijse.project.drivemaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student;
    @ManyToOne(optional=false) @JoinColumn(name="course_id") private Course course;
    @ManyToOne(optional=false) @JoinColumn(name="instructor_id") private Instructor instructor;

    @NotNull private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING) private LessonStatus status = LessonStatus.BOOKED;
}

