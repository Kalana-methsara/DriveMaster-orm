package lk.ijse.project.drivemaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    private String id; // S001

    @NotBlank
    private String firstName;
    @NotBlank private String lastName;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    private String gender;

    @NotBlank
    private String address;

    @NotBlank
    private String nic;

    @Email
    @NotBlank @Column(unique=true) private String email;
    @Pattern(regexp="^(?:0|\\+94)7\\d{8}$") @NotBlank private String phone;
    @NotNull
    private LocalDate regDate;

    @OneToMany(mappedBy="student", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments ;

    @OneToMany(mappedBy="student", cascade=CascadeType.ALL)
    private List<Payment> payments ;

    @OneToMany(mappedBy="student", cascade=CascadeType.ALL)
    private List<Lesson> lessons;

}
