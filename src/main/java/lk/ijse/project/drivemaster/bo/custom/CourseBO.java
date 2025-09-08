package lk.ijse.project.drivemaster.bo.custom;

import lk.ijse.project.drivemaster.bo.SuperBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.StudentDTO;

import java.util.List;

public interface CourseBO extends SuperBO {
    List<CourseDTO> getAllCourses() throws Exception;

    boolean saveCourse(CourseDTO courseDTO) throws Exception;

    boolean updateCourse(CourseDTO courseDTO) throws Exception;

    boolean deleteCourse(String id) throws Exception;

    List<String> getAllIds(String id) ;
}
