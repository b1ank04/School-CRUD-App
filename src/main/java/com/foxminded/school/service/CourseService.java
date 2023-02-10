package com.foxminded.school.service;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.jdbc.JDBCCourseDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements CourseDao {

    private final JDBCCourseDao jdbcCourseDao;

    public CourseService(JDBCCourseDao jdbcCourseDao) {
        this.jdbcCourseDao = jdbcCourseDao;
    }

    public Course save(Course course) throws SQLException {
        return jdbcCourseDao.save(course);
    }

    public Optional<Course> findById(Long id) {
        return jdbcCourseDao.findById(id);
    }

    public List<Course> findAll() {
        return jdbcCourseDao.findAll();
    }

    public void deleteById(Long id) throws SQLException {
        jdbcCourseDao.deleteById(id);
    }

    public List<Student> findRelatedStudents(Long id) {
        return jdbcCourseDao.findRelatedStudents(id);
    }
}
