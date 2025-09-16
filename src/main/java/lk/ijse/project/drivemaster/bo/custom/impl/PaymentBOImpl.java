package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.PaymentBO;
import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.dao.custom.StudentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.PAYMENT);

    @Override
    public Long getLastId() {
        Long lastId = paymentDAO.getLastId();
        if (lastId != null) {
            return lastId + 1;
        }
        return 1001L;    }
}
