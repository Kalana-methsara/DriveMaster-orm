package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Payment;
import lk.ijse.project.drivemaster.entity.Student;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface PaymentDAO extends CrudDAO<Payment> {
    List<String> getAllIds();
    Optional<Payment> findById(String id);

    boolean save(Payment payment, Session session);
    String getLastId();

    List<Payment> getStudentPayments(String id);

    boolean updatePaymentStatus(String id, String choice);
}
