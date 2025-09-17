package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.EnrollmentDAO;
import lk.ijse.project.drivemaster.entity.Enrollment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class EnrollmentDAOImpl implements EnrollmentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Enrollment> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Enrollment> query = session.createQuery("from Enrollment", Enrollment.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Enrollment enrollment) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(enrollment);
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
    public boolean update(Enrollment enrollment) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(enrollment);
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
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Enrollment enrollment = session.get(Enrollment.class, Long.valueOf(id)); // Enrollment PK = Long
            if (enrollment != null) {
                session.remove(enrollment);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Enrollment> findById(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            Enrollment enrollment = session.get(Enrollment.class, id);
            return Optional.ofNullable(enrollment);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Enrollment enrollment, Session session) {
        try {
            session.merge(enrollment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT e.id FROM Enrollment e ORDER BY e.id DESC",
                    String.class
            ).setMaxResults(1);
            List<String> list = query.list();
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT e.id FROM Enrollment e", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }
}
