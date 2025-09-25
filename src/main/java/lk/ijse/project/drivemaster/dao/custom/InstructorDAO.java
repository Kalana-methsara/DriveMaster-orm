package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Instructor;

import java.util.List;
import java.util.Optional;

public interface InstructorDAO extends CrudDAO<Instructor> {
    List<String> getAllIds(String id) ;
    Optional<Instructor> findById(String id);
    String getLastId();

    int getInstructorCount();
}
