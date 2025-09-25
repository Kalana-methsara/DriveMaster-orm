package lk.ijse.project.drivemaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private String id;
    private String studentId;
    private String courseId;
    private String  instructorId;
    private String startTime;
    private String endTime;
    private LocalDate lessonDate;
    private String status;
}
