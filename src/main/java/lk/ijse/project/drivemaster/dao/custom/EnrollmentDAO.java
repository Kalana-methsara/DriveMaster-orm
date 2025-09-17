package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Enrollment;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface EnrollmentDAO extends CrudDAO<Enrollment> {
    List<String> getAllIds();
    Optional<Enrollment> findById(String id);
    boolean save(Enrollment enrollment, Session session);
    String getLastId();

}
