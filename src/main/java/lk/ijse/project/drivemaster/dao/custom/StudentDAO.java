package lk.ijse.project.drivemaster.dao.custom;

import lk.ijse.project.drivemaster.dao.CrudDAO;
import lk.ijse.project.drivemaster.entity.Student;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentDAO extends CrudDAO<Student> {

    List<String> getAllIds(String id) ;

    Optional<Student> findById(String nic);

    String getLastId();

    boolean save(Student student, Session  session);

    List<Student> searchStudentByDate(String yearMonth);

    List<Student> searchStudent(String searchText);

    int getStudentCount();
}
