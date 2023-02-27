package com.foxminded.school.service;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import com.foxminded.school.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService{

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        Optional<Course> entity = findById(id);
        if (entity.isPresent()) {
            courseRepository.deleteById(id);
        } else throw new SQLException("Course with id="+ id + " doesn't exist");
    }

    @Transactional(readOnly = true)
    public List<Student> findRelatedStudents(Long id) {
        return courseRepository.findRelatedStudents(id);
    }
}
