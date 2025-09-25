package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.PaymentBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.InstructorDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;
import lk.ijse.project.drivemaster.entity.Instructor;
import lk.ijse.project.drivemaster.entity.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.PAYMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();


    @Override
    public List<PaymentDTO> getAllPayments() throws Exception {
        List<Payment> payments = paymentDAO.getAll();
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOS.add(converter.getPaymentDTO(payment));
        }
        return paymentDTOS;
    }

    @Override
    public String getNextId() {
        String lastId = paymentDAO.getLastId();
        char tableChar = 'P';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%03d", tableChar, nextIdNumber);
        }
        return tableChar + "001";
    }

    @Override
    public boolean savePayment(PaymentDTO dto) throws Exception {
        Optional<Payment> optionalPayment = paymentDAO.findById(dto.getId());
        if (optionalPayment.isPresent()) {
            throw new DuplicateException("Duplicate Payment id");
        }
        Payment payment = converter.getPayment(dto);
        return paymentDAO.save(payment);
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) throws Exception {
        Optional<Payment> optionalPayment = paymentDAO.findById(dto.getId());
        if (!optionalPayment.isPresent()) {
            throw new RuntimeException("Payment not found");
        }
        Payment payment = converter.getPayment(dto);
        return paymentDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String id) throws Exception {
        Optional<Payment> optionalPayment = paymentDAO.findById(id);
        if (optionalPayment.isEmpty()) {
            throw new NotFoundException("Payment not found..!");
        }
        return paymentDAO.delete(id);
    }

    @Override
    public List<PaymentDTO> getStudentPayments(String id) {
        return paymentDAO.getStudentPayments(id).stream()
                .map(converter::getPaymentDTO)
                .toList();
    }

    @Override
    public boolean updatePaymentStatus(String id, String choice) {
        return paymentDAO.updatePaymentStatus( id, choice);
    }


}
