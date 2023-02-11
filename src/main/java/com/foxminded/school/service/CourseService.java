package com.foxminded.school.service;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.jdbc.JDBCCourseDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements CourseDao {

    private final JDBCCourseDao jdbcCourseDao;

    public CourseService(JDBCCourseDao jdbcCourseDao) {
        this.jdbcCourseDao = jdbcCourseDao;
    }

    @Transactional
    public Course save(Course course) throws SQLException {
        return jdbcCourseDao.save(course);
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return jdbcCourseDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return jdbcCourseDao.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        jdbcCourseDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> findRelatedStudents(Long id) {
        return jdbcCourseDao.findRelatedStudents(id);
    }
}
