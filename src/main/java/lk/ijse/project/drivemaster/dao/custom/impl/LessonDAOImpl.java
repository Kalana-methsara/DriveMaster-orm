package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.LessonDAO;
import lk.ijse.project.drivemaster.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class LessonDAOImpl implements LessonDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Lesson> getAll() {
        return List.of();
    }

    @Override
    public boolean save(Lesson lesson) {
        return false;
    }

    @Override
    public boolean update(Lesson lesson) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
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
}
