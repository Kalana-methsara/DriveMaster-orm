package lk.ijse.project.drivemaster.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lk.ijse.project.drivemaster.entity.Instructor;
import lk.ijse.project.drivemaster.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<Lesson> lessons;

    public InstructorDTO(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
