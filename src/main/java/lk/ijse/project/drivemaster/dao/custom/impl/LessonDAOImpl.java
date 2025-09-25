package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.LessonDAO;
import lk.ijse.project.drivemaster.entity.Instructor;
import lk.ijse.project.drivemaster.entity.Lesson;
import lk.ijse.project.drivemaster.enums.LessonStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LessonDAOImpl implements LessonDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Lesson> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Lesson> query = session.createQuery("from Lesson ", Lesson.class);
            List<Lesson> lessonList = query.list();
            return lessonList;
        } finally {
            session.close();
        }    }

    @Override
    public boolean save(Lesson lesson) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(lesson);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Lesson lesson) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(lesson);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }        }

    @Override
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Lesson lesson = session.get(Lesson.class, id);
            if (lesson != null) {
                session.remove(lesson);
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
    public int SearchBookingLesson(LocalDate date, String inTime, String instructorId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "SELECT COUNT(l) FROM Lesson l " +
                    "WHERE l.lessonDate = :date " +
                    "AND l.startTime = :inTime " +
                    "AND l.instructor.id = :instructorId";

            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("date", date);
            query.setParameter("inTime", inTime);
            query.setParameter("instructorId", instructorId);

            Long count = query.uniqueResult();
            transaction.commit();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<String> query = session.createQuery(
                    "SELECT i.id FROM Lesson i ORDER BY i.id DESC",
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
    public Optional<Lesson> findById(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            Lesson lesson = session.get(Lesson.class, id);
            return Optional.ofNullable(lesson);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Lesson> getAllLessonsById(String id) {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Lesson> query = session.createQuery(
                    "FROM Lesson l WHERE l.student.id = :id", Lesson.class
            );
            query.setParameter("id", id);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updateLessonStatus(String id, String choice) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Lesson lesson = session.get(Lesson.class, id);
            if (lesson != null) {
                lesson.setStatus(LessonStatus.valueOf(choice)); // use your entity setter
                session.merge(lesson);    // update the entity
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


}
