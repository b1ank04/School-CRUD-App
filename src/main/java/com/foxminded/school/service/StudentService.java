package com.foxminded.school.service;

import com.foxminded.school.repository.CourseRepository;
import com.foxminded.school.repository.StudentRepository;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        Optional<Student> entity = findById(id);
        if (entity.isPresent()) {
            studentRepository.deleteById(id);
        } else throw new SQLException("Student with id="+ id + " doesn't exist");
    }

    @Transactional(readOnly = true)
    public List<Course> findRelatedCourses(Long id) {
        Optional<Student> student = findById(id);
        if (student.isPresent()) {
            return studentRepository.findRelatedCourses(id);
        } else throw new IllegalArgumentException("Student doesn't exist");
    }

    @Transactional
    public void addCourse(Long studentId, Long courseId) throws SQLException {
        Optional<Student> student = findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (student.isPresent() && course.isPresent()) {
            if (!student.get().getCourses().contains(course.get())) {
                studentRepository.addCourse(studentId, courseId);
            } else throw new SQLException("Course with id=1000 was added before");
        } else throw new SQLException("Student or Course with given IDs don't exist");
    }

    @Transactional
    public void removeCourse(Long studentId, Long courseId) throws SQLException {
        Optional<Student> student = findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (student.isPresent() && course.isPresent()) {
            studentRepository.removeCourse(studentId, courseId);
        } else throw new SQLException("Student or Course with given IDs doesn't exist");
    }
}
