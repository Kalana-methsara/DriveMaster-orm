package lk.ijse.project.drivemaster.config;



import lk.ijse.project.drivemaster.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration();
        configuration.configure();


        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Enrollment.class);
        configuration.addAnnotatedClass(Instructor.class);
        configuration.addAnnotatedClass(Lesson.class);
        configuration.addAnnotatedClass(Payment.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(User.class);

        // 3 create session factory
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration()
                :
                factoryConfiguration;
    }

    public Session getSession() {
        Session session = sessionFactory.openSession();
        return session;
    }

    // return the same session object for the
    // current session
    //
    // thread bound session

    // auto close happens on transaction commit
    // or rollback
    //
    // recommend for layered dao + service(bo) architecture
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    // session is not Thread safe
    // session factory is Thread safe, immutable
}
