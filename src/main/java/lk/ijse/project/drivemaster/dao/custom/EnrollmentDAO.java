package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentDAO extends CrudDAO<Enrollment> {
    List<Long> getAllIds();
    Optional<Enrollment> findById(Long id);
}
