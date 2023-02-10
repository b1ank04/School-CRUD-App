package com.foxminded.school.service;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.dao.jdbc.JDBCStudentDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements StudentDao {

    private final JDBCStudentDao jdbcStudentDao;

    public StudentService(JDBCStudentDao jdbcStudentDao) {
        this.jdbcStudentDao = jdbcStudentDao;
    }

    public Student save(Student student) throws SQLException {
        return jdbcStudentDao.save(student);
    }

    public Optional<Student> findById(Long id) {
        return jdbcStudentDao.findById(id);
    }

    public List<Student> findAll() {
        return jdbcStudentDao.findAll();
    }

    public void deleteById(Long id) throws SQLException {
        jdbcStudentDao.deleteById(id);
    }

    public List<Course> findRelatedCourses(Long id) {
        return jdbcStudentDao.findRelatedCourses(id);
    }

    public void addCourse(Long studentId, Long courseId) throws SQLException {
        jdbcStudentDao.addCourse(studentId, courseId);
    }

    public void deleteCourse(Long studentId, Long courseId) {
        jdbcStudentDao.deleteCourse(studentId, courseId);
    }
}
