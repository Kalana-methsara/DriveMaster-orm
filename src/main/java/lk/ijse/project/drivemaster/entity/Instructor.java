package lk.ijse.project.drivemaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @Email
    @NotBlank @Column(unique=true) private String email;
    @Pattern(regexp="^(?:0|\\+94)7\\d{8}$") @NotBlank private String phone;

    @OneToMany(mappedBy="instructor")
    private List<Lesson> lessons;


}
