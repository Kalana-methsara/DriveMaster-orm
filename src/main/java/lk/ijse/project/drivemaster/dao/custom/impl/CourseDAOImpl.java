package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.dao.custom.CourseDAO;
import lk.ijse.project.drivemaster.entity.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    @Override
    public List<Course> getAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }

    @Override
    public boolean save(Course course) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Course course) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
