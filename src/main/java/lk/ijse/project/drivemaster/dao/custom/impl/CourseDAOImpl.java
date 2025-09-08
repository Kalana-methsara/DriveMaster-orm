package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.CourseDAO;
import lk.ijse.project.drivemaster.entity.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();


    @Override
    public List<Course> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Course> query = session.createQuery("from Course", Course.class);
            List<Course> courseList = query.list();
            return courseList;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(Course course) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(course);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }    }

    @Override
    public boolean update(Course course) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(course);
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
            Course course = session.get(Course.class, id);
            if (course != null) {
                session.remove(course);
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
            Query<String> query = session.createQuery("SELECT c.id FROM Course c", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

}
