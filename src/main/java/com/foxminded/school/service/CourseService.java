package com.foxminded.school.service;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.jdbc.JPACourseDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements CourseDao {

    private final JPACourseDao jpaCourseDao;

    public CourseService(JPACourseDao jpaCourseDao) {
        this.jpaCourseDao = jpaCourseDao;
    }

    @Transactional
    public Course save(Course course) throws SQLException {
        return jpaCourseDao.save(course);
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return jpaCourseDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return jpaCourseDao.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        jpaCourseDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> findRelatedStudents(Long id) {
        return jpaCourseDao.findRelatedStudents(id);
    }
}
