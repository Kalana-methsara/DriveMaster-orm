package lk.ijse.project.drivemaster.entity;

import at.favre.lib.crypto.bcrypt.BCrypt;
//import org.mindrot.jbcrypt.BCrypt;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lk.ijse.project.drivemaster.enums.Role;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users", uniqueConstraints={
        @UniqueConstraint(columnNames={"username"}),
        @UniqueConstraint(columnNames={"email"})
})
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String username;

    @Email
    @NotBlank private String email;

    @NotBlank
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;

    public void setRawPassword(String raw) {
        this.passwordHash = BCrypt.withDefaults().hashToString(12, raw.toCharArray());
    }
    public boolean matches(String raw) {
//        boolean matched = BCrypt.checkpw(raw, passwordHash);
//
//        System.out.println("Password Matched: " + matched);
//        return matched;
        return BCrypt.verifyer().verify(raw.toCharArray(), passwordHash).verified;

    }
}
