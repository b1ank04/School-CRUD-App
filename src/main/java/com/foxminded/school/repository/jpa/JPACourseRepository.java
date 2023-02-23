package com.foxminded.school.repository.jpa;

import com.foxminded.school.repository.CourseRepository;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JPACourseRepository {

    private final CourseRepository courseRepository;

    public JPACourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course entity) throws SQLException {
        if (entity.getId() == null) {
            return courseRepository.save(entity);
        } else if (courseRepository.findById(entity.getId()).isPresent()) {
            return courseRepository.save(entity);
        } else throw new SQLException(String.format("Course with id=%d doesn't exist", entity.getId()));
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public void deleteById(Long id) throws SQLException {
        Optional<Course> entity = findById(id);
        if (entity.isPresent()) {
            courseRepository.deleteById(id);
        } else throw new SQLException("Course with id="+ id + " doesn't exist");
    }

    public List<Student> findRelatedStudents(Long id) throws SQLException {
        Optional<Course> entity = findById(id);
        if (entity.isPresent()) {
            return entity.get().getStudents().stream().toList();
        } else throw new SQLException("Student with id="+ id + " doesn't exist");
    }
}
