package lk.ijse.project.drivemaster.bo.custom.impl;

import lk.ijse.project.drivemaster.bo.custom.InstructorBO;
import lk.ijse.project.drivemaster.bo.exception.DuplicateException;
import lk.ijse.project.drivemaster.bo.exception.NotFoundException;
import lk.ijse.project.drivemaster.bo.util.EntityDTOConverter;
import lk.ijse.project.drivemaster.dao.custom.InstructorDAO;
import lk.ijse.project.drivemaster.dao.util.DAOFactoryImpl;
import lk.ijse.project.drivemaster.dao.util.DAOType;
import lk.ijse.project.drivemaster.dto.CourseDTO;
import lk.ijse.project.drivemaster.dto.InstructorDTO;
import lk.ijse.project.drivemaster.entity.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InstructorBOImpl implements InstructorBO {

    private final InstructorDAO instructorDAO = DAOFactoryImpl.getInstance().getDAO(DAOType.INSTRUCTOR);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<InstructorDTO> getAllInstructors() throws Exception {
        List<Instructor> instructors = instructorDAO.getAll();
        List<InstructorDTO> instructorDTOS = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorDTOS.add(converter.getInstructorDTO(instructor));
        }
        return instructorDTOS;
    }

    @Override
    public boolean saveInstructor(InstructorDTO dto) throws Exception {
        Optional<Instructor> optionalInstructor = instructorDAO.findById(dto.getId());
        if (optionalInstructor.isPresent()) {
            throw new DuplicateException("Duplicate Instructor id");
        }
        Instructor instructor = converter.getInstructor(dto);
        return instructorDAO.save(instructor);    }

    @Override
    public boolean updateInstructor(InstructorDTO dto) throws Exception {
        Optional<Instructor> optionalInstructor = instructorDAO.findById(dto.getId());
        if (!optionalInstructor.isPresent()) {
            throw new RuntimeException("Instructor not found");
        }
        Instructor instructor = converter.getInstructor(dto);
        return instructorDAO.update(instructor);    }

    @Override
    public boolean deleteInstructor(String id) throws Exception {
        Optional<Instructor> optionalInstructor = instructorDAO.findById(id);
        if (optionalInstructor.isEmpty()) {
            throw new NotFoundException("Instructor not found..!");
        }
        return instructorDAO.delete(id);    }

    @Override
    public List<String> getAllIds(String id) {
        List<Instructor> instructors = instructorDAO.getAll();
        List<String> ids = new ArrayList<>();
        for (Instructor instructor : instructors) {
            ids.add(String.valueOf(instructor.getId()));
        }
        return ids;      }

    @Override
    public String getNextId() {
        String lastId = instructorDAO.getLastId();
        char tableChar = 'I';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%03d", tableChar, nextIdNumber);
        }
        return tableChar + "001";   }

    @Override
    public InstructorDTO searchInstructor(String id) {
        Optional<Instructor> optionalInstructor = instructorDAO.findById(id);
        if (optionalInstructor.isPresent()) {
            Instructor instructor = optionalInstructor.get();
            return new InstructorDTO(
                    instructor.getId(),
                    instructor.getName(),
                    instructor.getNic(),
                    instructor.getEmail(),
                    instructor.getPhone()
            );
        } else {
            return null;
        }
    }
}
