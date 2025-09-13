package lk.ijse.project.drivemaster.dto;

import lk.ijse.project.drivemaster.entity.Course;
import lk.ijse.project.drivemaster.entity.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private String id;
    private String name;
    private String duration;
    private BigDecimal fee;
    private List<Enrollment> enrollments;

    public CourseDTO(String id, String name, String duration, BigDecimal fee) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
    }



    @Override
    public String toString() {
        return id + " - " + name + " | Duration: " + duration + " | Fee: " + fee;
    }


}
