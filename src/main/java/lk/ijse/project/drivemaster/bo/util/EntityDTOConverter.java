package lk.ijse.project.drivemaster.bo.util;

import lk.ijse.project.drivemaster.dto.*;
import lk.ijse.project.drivemaster.entity.*;
import lk.ijse.project.drivemaster.enums.LessonStatus;
import lk.ijse.project.drivemaster.enums.PaymentStatus;
import lk.ijse.project.drivemaster.enums.Role;

import java.time.LocalDateTime;

public class EntityDTOConverter {
    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(String.valueOf((user.getRole())));
        userDTO.setActive(user.isActive());
        return userDTO;
    }

    public User getUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setActive(dto.isActive());
        return user;
    }

    public StudentDTO getStudentDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getBirthday(), // fixed name
                student.getGender(),
                student.getAddress(),
                student.getEmail(),
                student.getPhone(),
                student.getRegDate(),
                student.getEnrollments() != null ? student.getEnrollments()
                        .stream()
                        .map(this::getEnrollmentDTO) // convert to DTO
                        .toList() : null,
                student.getPayments() != null ? student.getPayments()
                        .stream()
                        .map(this::getPaymentDTO) // convert to DTO
                        .toList() : null
        );
    }

    public Student getStudent(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setBirthday(dto.getBirthDate()); // fixed name
        student.setGender(dto.getGender());
        student.setAddress(dto.getAddress());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setRegDate(dto.getRegDate());

        if (dto.getEnrollments() != null) {
            student.setEnrollments(dto.getEnrollments()
                    .stream()
                    .map(this::getEnrollment) // convert back to entity
                    .toList());
        }

        if (dto.getPayments() != null) {
            student.setPayments(dto.getPayments()
                    .stream()
                    .map(this::getPayment) // convert back to entity
                    .toList());
        }

        return student;
    }

    public PaymentDTO getPaymentDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getStudent() != null ? payment.getStudent().getId() : null,
                payment.getAmount(),
                payment.getMethod(),
                payment.getCreatedAt(),
                payment.getStatus() != null ? payment.getStatus().name() : null,
                payment.getReference()
        );
    }


    public Payment getPayment(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());

        if (dto.getStudentId() != null) {
            Student student = new Student();
            student.setId(dto.getStudentId());
            payment.setStudent(student);
        }

        payment.setAmount(dto.getAmount());
        payment.setMethod(dto.getMethod());
        payment.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        if (dto.getStatus() != null) {
            payment.setStatus(PaymentStatus.valueOf(dto.getStatus()));
        }
        payment.setReference(dto.getReference());

        return payment;
    }


    public EnrollmentDTO getEnrollmentDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
                enrollment.getId(),
                enrollment.getStudent() != null ? enrollment.getStudent().getId() : null,
                enrollment.getCourse() != null ? enrollment.getCourse().getId() : null,
                enrollment.getRegDate(),
                enrollment.getUpfrontPaid(),
                enrollment.getCourse() != null ? getCourseDTO(enrollment.getCourse()) : null
        );
    }


    public Enrollment getEnrollment(EnrollmentDTO dto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());

        if (dto.getStudentId() != null) {
            Student student = new Student();
            student.setId(dto.getStudentId());
            enrollment.setStudent(student);
        }

        if (dto.getCourseId() != null) {
            Course course = new Course();
            course.setId(dto.getCourseId());
            enrollment.setCourse(course);
        }

        enrollment.setRegDate(dto.getRegDate());
        enrollment.setUpfrontPaid(dto.getUpfrontPaid());

        return enrollment;
    }
    public CourseDTO getCourseDTO(Course course) {
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDuration(),
                course.getFee()
        );
    }
    public Course getCourse(CourseDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setDuration(dto.getDuration());
        course.setFee(dto.getFee());
        return course;
    }
    public LessonDTO getLessonDTO(Lesson lesson) {
        return new LessonDTO(
                lesson.getId(),
                lesson.getStudent() != null ? lesson.getStudent().getId() : null,
                lesson.getCourse() != null ? lesson.getCourse().getId() : null,
                lesson.getInstructor() != null ? lesson.getInstructor().getId() : null,
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.getStatus() != null ? lesson.getStatus().name() : null
        );
    }
    public Lesson getLesson(LessonDTO dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.getId());

        if (dto.getStudentId() != null) {
            Student student = new Student();
            student.setId(dto.getStudentId());
            lesson.setStudent(student);
        }

        if (dto.getCourseId() != null) {
            Course course = new Course();
            course.setId(dto.getCourseId());
            lesson.setCourse(course);
        }

        if (dto.getInstructorId() != null) {
            Instructor instructor = new Instructor();
            instructor.setId(dto.getInstructorId());
            lesson.setInstructor(instructor);
        }

        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());

        if (dto.getStatus() != null) {
            lesson.setStatus(LessonStatus.valueOf(dto.getStatus()));
        }

        return lesson;
    }

    public InstructorDTO getInstructorDTO(Instructor instructor) {
        return new InstructorDTO(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getPhone()
        );
    }
    public Instructor getInstructor(InstructorDTO dto) {
        Instructor instructor = new Instructor();
        instructor.setId(dto.getId());
        instructor.setName(dto.getName());
        instructor.setEmail(dto.getEmail());
        instructor.setPhone(dto.getPhone());
        return instructor;
    }


}
