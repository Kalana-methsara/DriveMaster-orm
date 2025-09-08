package lk.ijse.project.drivemaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private String id;
    private String name;
    private String duration;
    private BigDecimal fee;
}
