package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.config.FactoryConfiguration;
import lk.ijse.project.drivemaster.dao.custom.UserDAO;
import lk.ijse.project.drivemaster.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<User> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<User> query = session.createQuery("from User", User.class);
            List<User> userList = query.list();
            return userList;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean save(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(user);
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
    public boolean update(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(user);
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
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
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
    public Optional<User> findById(Long id) {
        Session session = factoryConfiguration.getSession();
        try {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } finally {
            session.close();
        }
    }
    @Override
    public User search(String userName, String password) {
        Session session = factoryConfiguration.getSession();
        try {
            NativeQuery<User> query = session.createNativeQuery(
                    "SELECT * FROM users WHERE BINARY username = :username",
                    User.class
            );
            query.setParameter("username", userName);

            User user = query.uniqueResult();

            if (user != null) {
                if (user.matches(password)) {
                    System.out.println("Login successful!");
                    return user;
                } else {
                    System.out.println("Password incorrect!");
                }
            } else {
                System.out.println("User not found!");
            }
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public User findPassword(String userName, String email) {
        Session session = factoryConfiguration.getSession();
        try {
            NativeQuery<User> query = session.createNativeQuery(
                    "SELECT * FROM users WHERE BINARY username = :username",
                    User.class
            );
            query.setParameter("username", userName);

            User user = query.uniqueResult();

            if (user != null && user.getEmail().equals(email)) {
                    return user;
            } else {
                System.out.println("User not found!");
            }
            return null;
        } finally {
            session.close();
        }
    }


    @Override
    public boolean updatePassword(Long id, String password) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if (user != null) {
                user.setRawPassword(password);

                session.merge(user);
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
    public Long getLastId() {
        Session session = factoryConfiguration.getSession();
        try {
            Query<Long> query = session.createQuery(
                    "SELECT u.id FROM User u ORDER BY u.id DESC",
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


