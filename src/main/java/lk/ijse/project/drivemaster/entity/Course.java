package lk.ijse.project.drivemaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="courses")
public class Course {
    @Id
    private String id; // e.g., C1001
    @NotBlank private String name;
    @NotBlank
    private String duration; // "12 weeks", "6 months"
    @NotNull
    @Column(precision=10, scale=2) private BigDecimal fee;

    @OneToMany(mappedBy="course")
    private List<Enrollment> enrollments = new ArrayList<>();
}

