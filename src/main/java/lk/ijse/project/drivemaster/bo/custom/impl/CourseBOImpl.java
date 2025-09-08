package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.dto.CourseDTO;

import java.util.List;

public class CourseBOImpl implements CourseBO {
    @Override
    public List<CourseDTO> getAllCourses() throws Exception {
        return List.of();
    }

    @Override
    public boolean saveCourse(CourseDTO courseDTO) throws Exception {
        return false;
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) throws Exception {
        return false;
    }

    @Override
    public boolean deleteCourse(String id) throws Exception {
        return false;
    }

    @Override
    public List<String> getAllIds(String id) {
        return List.of();
    }
}
