package lk.ijse.project.drivemaster.bo;

import lk.ijse.project.drivemaster.bo.custom.impl.CourseBOImpl;
import lk.ijse.project.drivemaster.bo.custom.impl.PaymentBOImpl;
import lk.ijse.project.drivemaster.bo.custom.impl.StudentBOImpl;
import lk.ijse.project.drivemaster.bo.custom.impl.UserBOImpl;

public class BOFactoryImpl implements BOFactory{
    private static BOFactoryImpl boFactory;

    private BOFactoryImpl() {
    }

    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactoryImpl();
        }
        return boFactory;
    }
    public <T extends SuperBO> T getBO(BOType boType) {
        return switch (boType) {
            case COURSE -> (T) new CourseBOImpl();
            case PAYMENT ->  (T) new PaymentBOImpl();
            case STUDENT ->   (T) new StudentBOImpl();
            case USER -> (T) new UserBOImpl();
        };
    }
}
