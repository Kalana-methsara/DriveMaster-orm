package lk.ijse.project.drivemaster.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ijse.project.drivemaster.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="payments")
public class Payment {
    @Id
    private String id; // P001

    @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student;
    @NotNull
    @Column(precision=10, scale=2) private BigDecimal amount;
    @NotBlank
    private String method; // 'CASH','CARD','ONLINE'
    @NotNull private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING) private PaymentStatus status = PaymentStatus.PENDING;
    @Column(unique=true) private String reference; // receipt number
}
