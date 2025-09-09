package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.StudentDAO;
import lk.ijse.project.drivemaster.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Student> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Student> query = session.createQuery("from Student", Student.class);
            List<Student> studentList = query.list();
            return studentList;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Student student)  {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(student);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }      }

    @Override
    public boolean update(Student student) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(student);
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
    public boolean delete(String id)  {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.remove(student);
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
    public List<String> getAllIds(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery("SELECT s.id FROM Student s", String.class);
            return query.list();
        } finally {
            session.close();
        }    }

    @Override
    public Optional<Student> findById(Long id) {
        Session session = factoryConfiguration.getSession();
        try {
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        } finally {
            session.close();
        }
    }
}
