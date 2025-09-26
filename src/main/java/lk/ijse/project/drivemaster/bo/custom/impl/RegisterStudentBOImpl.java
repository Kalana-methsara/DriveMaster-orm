package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.RegisterStudentBO;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.CourseDAO;
import lk.ijse.project.drivemaster.dao.custom.EnrollmentDAO;
import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.dao.custom.StudentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import lk.ijse.project.drivemaster.entity.Enrollment;
import lk.ijse.project.drivemaster.entity.Payment;
import lk.ijse.project.drivemaster.entity.Student;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RegisterStudentBOImpl implements RegisterStudentBO {

    private final StudentDAO studentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.STUDENT);
    private final EnrollmentDAO enrollmentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.ENROLLMENT);
    private final PaymentDAO paymentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.PAYMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public boolean StudentRegistration(StudentDTO studentDTO, List<EnrollmentDTO> enrollments, PaymentDTO paymentDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            // Save Student
            boolean isStudentSaved = studentDAO.save(converter.getStudent(studentDTO), session);
            if (!isStudentSaved) {
                transaction.rollback();
                return false;
            }

            // Save Enrollments with reference to Student
            for (EnrollmentDTO enrollmentDTO : enrollments) {
                boolean isEnrollmentSaved = enrollmentDAO.save(converter.getEnrollment(enrollmentDTO), session);
                if (!isEnrollmentSaved) {
                    transaction.rollback();
                    return false;
                }
            }

            // Save Payment with reference to Student
            Payment payment = converter.getPayment(paymentDTO);
            payment.setStudent(converter.getStudent(studentDTO));
            boolean isPaymentSaved = paymentDAO.save(payment, session);

            if (!isPaymentSaved) {
                transaction.rollback();
                return false;
            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean UpdateRegistration(StudentDTO studentDTO, List<EnrollmentDTO> enrollments, PaymentDTO paymentDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            // Save Student
            boolean isStudentSaved = studentDAO.update(converter.getStudent(studentDTO), session);
            if (!isStudentSaved) {
                transaction.rollback();
                return false;
            }

            // Save Enrollments with reference to Student
            for (EnrollmentDTO enrollmentDTO : enrollments) {
                boolean isEnrollmentSaved = enrollmentDAO.save(converter.getEnrollment(enrollmentDTO), session);
                if (!isEnrollmentSaved) {
                    transaction.rollback();
                    return false;
                }
            }

            // Save Payment with reference to Student
            Payment payment = converter.getPayment(paymentDTO);
            payment.setStudent(converter.getStudent(studentDTO));
            boolean isPaymentSaved = paymentDAO.save(payment, session);

            if (!isPaymentSaved) {
                transaction.rollback();
                return false;
            }

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
}
