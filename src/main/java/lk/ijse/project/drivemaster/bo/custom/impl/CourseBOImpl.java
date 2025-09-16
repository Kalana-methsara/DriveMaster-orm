package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.CourseBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.CourseDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseBOImpl implements CourseBO {

    private final CourseDAO courseDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.COURSE);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<CourseDTO> getAllCourses() throws Exception {
        List<Course> courses = courseDAO.getAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(converter.getCourseDTO(course));
        }
        return courseDTOS;
    }

    @Override
    public boolean saveCourse(CourseDTO dto) throws Exception {
        Optional<Course> optionalCourse = courseDAO.findById(Long.valueOf(dto.getId()));
        if (optionalCourse.isPresent()) {
            throw new DuplicateException("Duplicate course id");
        }
        Course course = converter.getCourse(dto);
        return courseDAO.save(course);
    }

    @Override
    public boolean updateCourse(CourseDTO dto) throws Exception {
        Optional<Course> optionalCourse = courseDAO.findById(Long.valueOf(dto.getId()));
        if (optionalCourse.isPresent()) {
            throw new RuntimeException("Course not found");
        }
        Course course = converter.getCourse(dto);
        return courseDAO.update(course);
    }

    @Override
    public boolean deleteCourse(String id) throws Exception {
        Optional<Course> optionalCourse = courseDAO.findById(Long.valueOf(id));
        if (optionalCourse.isEmpty()) {
            throw new NotFoundException("Course not found..!");
        }
        return courseDAO.delete(id);
    }

    @Override
    public List<String> getAllIds(String id) {
        List<Course> courses = courseDAO.getAll();
        List<String> ids = new ArrayList<>();
        for (Course course : courses) {
            ids.add(String.valueOf(course.getId()));
        }
        return ids;    }

    @Override
    public String getNextId() {
        String lastId = courseDAO.getLastId();
        char tableChar = 'C';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%04d", tableChar, nextIdNumber);
        }
        return tableChar + "1001"; // start from C1001
    }
    }

