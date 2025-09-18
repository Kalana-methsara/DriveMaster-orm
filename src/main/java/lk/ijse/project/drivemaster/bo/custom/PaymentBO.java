package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    String getNextId();

    List<PaymentDTO> getStudentPayments(String id);
}
