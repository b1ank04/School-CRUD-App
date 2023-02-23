package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

@Repository
public class JPAStudentDao extends AbstractCrudDao<Student, Long> implements StudentDao {

    @PersistenceContext
    EntityManager em;

    @Override
    protected Student create(Student entity) {
        em.persist(entity);
        return entity;
    }



    @Override
    protected Student update(Student entity) throws SQLException {
        Optional<Student> student = findById(entity.getId());
        if (student.isPresent()) {
            return em.merge(entity);
        } else throw new SQLException("Student with id="+ entity.getId() + " doesn't exist");
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(em.find(Student.class, id));
    }

    @Override
    public List<Student> findAll() {
        return em.createQuery("from Student", Student.class).getResultList();
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Optional<Student> entity = findById(id);
        if (entity.isPresent()) {
            em.remove(entity.get());
        } else throw new SQLException("Student with id="+ id + " doesn't exist");
    }

    @Override
    public List<Course> findRelatedCourses(Long id) {
        Optional<Student> student = findById(id);
        if (student.isPresent()) {
            return student.get().getCourses().stream().toList();
        } else throw new IllegalArgumentException("Student doesn't exist");
    }

     @Override
    public void addCourse(Long studentId, Long courseId) throws SQLException {
        Optional<Student> student = findById(studentId);
        Optional<Course> course = Optional.ofNullable(em.find(Course.class, courseId));
        if (student.isPresent() && course.isPresent()) {
            student.get().addCourse(course.get());
        } else throw new SQLException("Student or Course with given IDs don't exist");
    }

    @Override
    public void removeCourse(Long studentId, Long courseId) throws SQLException {
        Optional<Student> student = findById(studentId);
        Optional<Course> course = Optional.ofNullable(em.find(Course.class, courseId));
        if (student.isPresent() && course.isPresent()) {
            student.get().removeCourse(course.get());
        } else throw new SQLException("Student or Course with given IDs doesn't exist");
    }
}
