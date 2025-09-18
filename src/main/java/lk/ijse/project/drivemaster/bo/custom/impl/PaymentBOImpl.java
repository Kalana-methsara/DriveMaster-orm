package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.PaymentBO;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.PaymentDTO;

import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.PAYMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();


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
    public List<PaymentDTO> getStudentPayments(String id) {
        return paymentDAO.getStudentPayments(id).stream()
                .map(converter::getPaymentDTO)
                .toList();
    }


}
