package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.EnrollmentBO;
import lk.ijse.project.drivemaster.dao.custom.EnrollmentDAO;
import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;

public class EnrollmentBOImpl implements EnrollmentBO {

    private final EnrollmentDAO enrollmentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.ENROLLMENT);

    @Override
    public String getLastId() {
        String lastId = enrollmentDAO.getLastId();
        char tableChar = 'E';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%03d", tableChar, nextIdNumber);
        }
        return tableChar + "001";
    }
}
