package com.foxminded.school.service;

import com.foxminded.school.repository.jpa.JPACourseRepository;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService{

    private final JPACourseRepository jpaCourseRepository;

    public CourseService(JPACourseRepository jpaCourseRepository) {
        this.jpaCourseRepository = jpaCourseRepository;
    }

    @Transactional
    public Course save(Course course) throws SQLException {
        return jpaCourseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return jpaCourseRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return jpaCourseRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        jpaCourseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> findRelatedStudents(Long id) throws SQLException {
        return jpaCourseRepository.findRelatedStudents(id);
    }
}
