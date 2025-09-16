package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.InstructorDAO;
import lk.ijse.project.drivemaster.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();


    @Override
    public List<Instructor> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Instructor> query = session.createQuery("from Instructor", Instructor.class);
            List<Instructor> instructorList = query.list();
            return instructorList;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Instructor instructor) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(instructor);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }     }

    @Override
    public boolean update(Instructor instructor) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(instructor);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }    }

    @Override
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Instructor instructor = session.get(Instructor.class, id);
            if (instructor != null) {
                session.remove(instructor);
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
        }    }

    @Override
    public List<String> getAllIds(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT i.id FROM Instructor i", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        Session session = factoryConfiguration.getSession();
        try {
            Instructor instructor = session.get(Instructor.class, id);
            return Optional.ofNullable(instructor);
        } finally {
            session.close();
        }    }

    @Override
    public Long getLastId() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Long> query = session.createQuery(
                    "SELECT i.id FROM Instructor i ORDER BY i.id DESC",
                    Long.class
            ).setMaxResults(1);

            List<Long> list = query.list();
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);

        } finally {
            session.close();
        }
    }
}
