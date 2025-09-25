package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    List<PaymentDTO> getAllPayments() throws Exception;

    String getNextId();
    boolean savePayment(PaymentDTO paymentDTO) throws Exception;
    boolean updatePayment(PaymentDTO paymentDTO) throws Exception;
    boolean deletePayment(String id) throws Exception;

    List<PaymentDTO> getStudentPayments(String id);

    boolean updatePaymentStatus(String id, String choice);
}
