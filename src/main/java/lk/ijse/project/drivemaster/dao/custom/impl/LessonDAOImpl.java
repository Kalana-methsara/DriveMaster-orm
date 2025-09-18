package lk.ijse.project.drivemaster.dao.custom.impl;

import lk.ijse.project.drivemaster.dao.custom.LessonDAO;
import lk.ijse.project.drivemaster.entity.Lesson;

import java.util.List;

public class LessonDAOImpl implements LessonDAO {
    @Override
    public List<Lesson> getAll() {
        return List.of();
    }

    @Override
    public boolean save(Lesson lesson) {
        return false;
    }

    @Override
    public boolean update(Lesson lesson) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
