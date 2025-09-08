package lk.ijse.project.drivemaster.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    List<T> getAll();
    boolean save(T t);
    boolean update(T t) ;
    boolean delete(String id);
}
