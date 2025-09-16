package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.PaymentDAO;
import lk.ijse.project.drivemaster.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Payment> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Payment> query = session.createQuery("from Payment", Payment.class);
            return query.list();
        } finally {
            session.close();
        }
    }
    @Override
   public boolean save(Payment payment,Session session){
       try {
           session.merge(payment);
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
    }

    @Override
    public Long getLastId() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Long> query = session.createQuery(
                    "SELECT p.id FROM Payment p ORDER BY p.id DESC",
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

    @Override
    public boolean save(Payment payment) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(payment);
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
    public boolean update(Payment payment) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(payment);
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
            Payment payment = session.get(Payment.class, Long.valueOf(id)); // Payment PK = Long
            if (payment != null) {
                session.remove(payment);
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
    public Optional<Payment> findById(Long id) {
        Session session = factoryConfiguration.getSession();
        try {
            Payment payment = session.get(Payment.class, id);
            return Optional.ofNullable(payment);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Long> getAllIds() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Long> query = session.createQuery("SELECT p.id FROM Payment p", Long.class);
            return query.list();
        } finally {
            session.close();
        }
    }
}
