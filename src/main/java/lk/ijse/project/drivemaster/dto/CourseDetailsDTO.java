package lk.ijse.project.drivemaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailsDTO {
    private String id;
    private String name;
    private String duration;
    private BigDecimal fee;
    private LocalDate endDate;
    private String status;

    }

