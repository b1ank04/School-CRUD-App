package com.foxminded.school.service;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.dao.jdbc.JPAStudentDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements StudentDao {

    private final JPAStudentDao jpaStudentDao;

    public StudentService(JPAStudentDao jpaStudentDao) {
        this.jpaStudentDao = jpaStudentDao;
    }

    @Transactional
    public Student save(Student student) throws SQLException {
        return jpaStudentDao.save(student);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return jpaStudentDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return jpaStudentDao.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        jpaStudentDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> findRelatedCourses(Long id) {
        return jpaStudentDao.findRelatedCourses(id);
    }

    @Transactional
    public void addCourse(Long studentId, Long courseId) throws SQLException {
        jpaStudentDao.addCourse(studentId, courseId);
    }

    @Transactional
    public void deleteCourse(Long studentId, Long courseId) {
        jpaStudentDao.deleteCourse(studentId, courseId);
    }
}
