package lk.ijse.project.drivemaster.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    List<T> getAll() throws SQLException,ClassNotFoundException;
    boolean save(T t) throws SQLException , ClassNotFoundException;
    boolean update(T t) throws SQLException , ClassNotFoundException;
    boolean delete(String id)  throws SQLException , ClassNotFoundException;
}
