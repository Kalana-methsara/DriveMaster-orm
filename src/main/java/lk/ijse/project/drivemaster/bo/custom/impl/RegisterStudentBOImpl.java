package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.RegisterStudentBO;
import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;
import lk.ijse.project.drivemaster.dto.PaymentDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RegisterStudentBOImpl implements RegisterStudentBO {
    @Override
    public void StudentRegistration(StudentDTO student, CourseDTO course, EnrollmentDTO enrollment, PaymentDTO payment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            // 1. Save/Update Student
            session.saveOrUpdate(student);

            // 2. Save/Update Course
            session.saveOrUpdate(course);


            session.saveOrUpdate(enrollment);

            session.saveOrUpdate(payment);

            // ✅ Commit if everything is successful
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
