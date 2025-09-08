package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Student;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {
    List<String> getAllIds(String id) ;

}
