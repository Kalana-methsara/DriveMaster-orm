package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.User;

import java.util.Optional;

public interface UserDAO extends CrudDAO<User> {
    Optional<User> findById(Long id) ;

    User search(String userName, String password);

    User findPassword(String userName, String email);

    String password(long id);
}
