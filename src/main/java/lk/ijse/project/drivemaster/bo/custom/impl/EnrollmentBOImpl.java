package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.EnrollmentBO;
import lk.ijse.project.drivemaster.dao.custom.EnrollmentDAO;
import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;

public class EnrollmentBOImpl implements EnrollmentBO {

    private final EnrollmentDAO enrollmentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.ENROLLMENT);

    @Override
    public Long getLastId() {
        Long lastId = enrollmentDAO.getLastId();
        if (lastId != null) {
            return lastId + 1;
        }
        return 1001L;
    }
}
