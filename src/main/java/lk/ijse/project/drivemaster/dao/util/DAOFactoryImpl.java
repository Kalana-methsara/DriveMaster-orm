package lk.ijse.project.drivemaster.dao.util;

import lk.ijse.project.drivemaster.dao.DAOFactory;
import lk.ijse.project.drivemaster.dao.SuperDAO;
import lk.ijse.project.drivemaster.dao.custom.impl.*;

public class DAOFactoryImpl implements DAOFactory {
    private static DAOFactoryImpl daoFactory;

    private DAOFactoryImpl() {

    }

    public static DAOFactoryImpl getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactoryImpl();
        }
        return daoFactory;
    }
    @SuppressWarnings("unchecked")
    public <T extends SuperDAO> T getDAO(DAOType daoType) {
        return switch (daoType) {
            case COURSE -> (T) new CourseDAOImpl();
            case PAYMENT ->  (T) new PaymentDAOImpl();
            case STUDENT ->   (T) new StudentDAOImpl();
            case USER -> (T) new UserDAOImpl();
            case ENROLLMENT -> (T) new EnrollmentDAOImpl();
            case INSTRUCTOR ->  (T) new InstructorDAOImpl();
            case QUERY -> (T) new QueryDAOImpl();
        };
    }
}
