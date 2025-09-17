package lk.ijse.project.drivemaster.dto;

import lk.ijse.project.drivemaster.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String id;
    private String studentId;
    private BigDecimal amount;
    private String method;
    private LocalDateTime createdAt;
    private String status;       // "PENDING","COMPLETE","FAILED"
    private String reference;



}
