package com.foxminded.school.repository.jpa;

import com.foxminded.school.repository.CourseRepository;
import com.foxminded.school.repository.StudentRepository;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
public class JPAStudentRepository {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public JPAStudentRepository(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public Student save(Student entity) throws SQLException {
        if (entity.getId() == null) {
            return studentRepository.save(entity);
        } else if (studentRepository.findById(entity.getId()).isPresent()) {
            return studentRepository.save(entity);
        } else throw new SQLException(String.format("Student with id=%d doesn't exist", entity.getId()));
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public void deleteById(Long id) throws SQLException {
        Optional<Student> entity = findById(id);
        if (entity.isPresent()) {
            studentRepository.deleteById(id);
        } else throw new SQLException("Student with id="+ id + " doesn't exist");
    }

    public List<Course> findRelatedCourses(Long id) {
        Optional<Student> student = findById(id);
        if (student.isPresent()) {
            return student.get().getCourses().stream().toList();
        } else throw new IllegalArgumentException("Student doesn't exist");
    }

    public void addCourse(Long studentId, Long courseId) throws SQLException {
        Optional<Student> student = findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (student.isPresent() && course.isPresent()) {
            student.get().addCourse(course.get());
        } else throw new SQLException("Student or Course with given IDs don't exist");
    }

    public void removeCourse(Long studentId, Long courseId) throws SQLException {
        Optional<Student> student = findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (student.isPresent() && course.isPresent()) {
            student.get().removeCourse(course.get());
        } else throw new SQLException("Student or Course with given IDs doesn't exist");
    }
}
