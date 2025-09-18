package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.EnrollmentBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.EnrollmentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.EnrollmentDTO;
import lk.ijse.project.drivemaster.entity.Course;
import lk.ijse.project.drivemaster.entity.Enrollment;

import java.util.List;
import java.util.Optional;

public class EnrollmentBOImpl implements EnrollmentBO {

    private final EnrollmentDAO enrollmentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.ENROLLMENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public boolean saveCourse(EnrollmentDTO dto) throws Exception {
        Optional<Enrollment> optionalEnrollment = enrollmentDAO.findById(dto.getId());
        if (optionalEnrollment.isPresent()) {
            throw new DuplicateException("Duplicate enrollment id");
        }
        Enrollment enrollment = converter.getEnrollment(dto);
        return enrollmentDAO.save(enrollment);
    }

    @Override
    public String getNextId() {
        String lastId = enrollmentDAO.getLastId();
        char tableChar = 'E';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%03d", tableChar, nextIdNumber);
        }
        return tableChar + "001";
    }

    @Override
    public List<EnrollmentDTO> getStudentCourses(String studentId) {
        return enrollmentDAO.getStudentCourses(studentId).stream()
                .map(converter::getEnrollmentDTO)
                .toList();    }

}
