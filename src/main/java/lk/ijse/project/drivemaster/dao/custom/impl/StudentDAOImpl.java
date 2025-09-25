package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.StudentDAO;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import lk.ijse.project.drivemaster.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Student> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Student> query = session.createQuery("from Student", Student.class);
            return query.list();
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
            if (transaction != null) transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

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
            Query<String> query = session.createQuery("SELECT CAST(s.id as string) FROM Student s", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Student> findById(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT s.id FROM Student s ORDER BY s.id DESC",
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
    public boolean save(Student student, Session session) {
        try {
            session.persist(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Student> searchStudentByDate(String yearMonth) {
        Session session = factoryConfiguration.getSession();
        try {
            // Convert "2008-September" to YearMonth
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMMM", Locale.ENGLISH);
            YearMonth ym = YearMonth.parse(yearMonth, formatter);

            LocalDate startDate = ym.atDay(1); // first day of month
            LocalDate endDate = ym.atEndOfMonth(); // last day of month

            Query<Student> query = session.createQuery(
                    "FROM Student s WHERE s.regDate BETWEEN :startDate AND :endDate",
                    Student.class
            );
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Student> searchStudent(String searchText) {
        Session session = factoryConfiguration.getSession();
        try {
            String hql = "FROM Student s WHERE " +
                    "s.id LIKE :text OR " +
                    "s.firstName LIKE :text OR " +
                    "s.lastName LIKE :text OR " +
                    "s.nic LIKE :text OR " +
                    "s.phone LIKE :text OR " +
                    "s.email LIKE :text OR " +
                    "s.gender LIKE :text OR " +
                    "s.address LIKE :text";

            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("text", "%" + searchText + "%");
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public int getStudentCount() {
        Session session = factoryConfiguration.getSession();
        try {
            Long count = session.createQuery("SELECT COUNT(s) FROM Student s", Long.class)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        } finally {
            session.close();
        }
    }



}
