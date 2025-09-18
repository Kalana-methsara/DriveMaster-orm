package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.StudentBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.StudentDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.StudentDTO;
import lk.ijse.project.drivemaster.entity.Student;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.STUDENT);
    private final EntityDTOConverter converter = new EntityDTOConverter();


    @Override
    public List<StudentDTO> getAllStudent() throws Exception {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(converter.getStudentDTO(student));
        }

        return studentDTOS;


    }

    @Override
    public boolean saveStudent(StudentDTO dto) throws Exception {
        Optional<Student> optionalStudent = studentDAO.findById(dto.getId());
        if (optionalStudent.isPresent()) {
            throw new DuplicateException("Duplicate student id");
        }
        Student student = converter.getStudent(dto);
        return studentDAO.save(student);
    }

    @Override
    public boolean updateStudent(StudentDTO dto) throws Exception {
        Optional<Student> optionalStudent = studentDAO.findById(dto.getId());
        if (!optionalStudent.isPresent()) {
            throw new RuntimeException("Student not found");
        }
        Student student = converter.getStudent(dto);
        return studentDAO.update(student);
    }

    @Override
    public boolean deleteStudent(String id) throws Exception {
        Optional<Student> optionalStudent = studentDAO.findById(id);
        if (optionalStudent.isEmpty()) {
            throw new NotFoundException("Customer not found..!");
        }
        return studentDAO.delete(id);    }

    @Override
    public List<String> getAllIds(String id) {
        List<Student> students = studentDAO.getAll();
        List<String> ids = new ArrayList<>();
        for (Student student : students) {
            ids.add(String.valueOf(student.getId()));
        }
        return ids;
    }

    @Override
    public StudentDTO getStudentById(String id) {
        Optional<Student> optionalStudent = studentDAO.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return new StudentDTO(
                    student.getId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getBirthday(),
                    student.getGender(),
                    student.getAddress(),
                    student.getNic(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getRegDate()
            );
        } else {
            return null; // or throw exception
        }
    }

    @Override
    public String getNextId() {
        String lastId = studentDAO.getLastId();
        char tableChar = 'S';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%03d", tableChar, nextIdNumber);
        }
        return tableChar + "001";
    }

    @Override
    public List<StudentDTO> searchStudentByDate(String yearMonth) {
        return studentDAO.searchStudentByDate(yearMonth).stream()
                .map(converter::getStudentDTO)
                .toList();
    }

    @Override
    public List<StudentDTO> searchStudent(String searchText) {
        return studentDAO.searchStudent(searchText).stream()
                .map(converter::getStudentDTO)
                .toList();    }


}
