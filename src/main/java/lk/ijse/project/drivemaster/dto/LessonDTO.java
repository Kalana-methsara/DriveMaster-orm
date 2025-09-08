package lk.ijse.project.drivemaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private Long studentId;
    private String courseId;
    private Long instructorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}
