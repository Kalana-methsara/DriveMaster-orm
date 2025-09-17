package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Course;


import java.util.List;
import java.util.Optional;

public interface CourseDAO extends CrudDAO<Course> {
    List<String> getAllIds(String id) ;
    Optional<Course> findById(String id);
    String getLastId();

}
