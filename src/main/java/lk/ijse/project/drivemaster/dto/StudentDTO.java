package lk.ijse.project.drivemaster.dto;

import lk.ijse.project.drivemaster.entity.Enrollment;
import lk.ijse.project.drivemaster.entity.Lesson;
import lk.ijse.project.drivemaster.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String gender;
    private String address;
    private String nic;
    private String email;
    private String phone;
    private LocalDate regDate;

}
