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
        return em.merge(entity);
    }

    @Override
    public Optional<Student> findById(Long id) {
        Student student = em.find(Student.class, id);
        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> findAll() {
        return em.createQuery("from Student", Student.class).getResultList();
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Optional<Student> entity = findById(id);
        entity.ifPresentOrElse(student -> em.remove(student), () -> System.out.println("Student doesn't exist"));
    }

    @Override
    public List<Course> findRelatedCourses(Long id) {
        Optional<Student> student = findById(id);
        if (student.isPresent()) {
            return em.createQuery("""
                                    SELECT c FROM Course c
                                    JOIN c.students s
                                    WHERE s.id = :studentId
                                    """, Course.class)
                    .setParameter("studentId", id)
                    .getResultList();
        }
        else throw new IllegalArgumentException("Student doesn't exist");
    }

     @Override
    public void addCourse(Long studentId, Long courseId) throws SQLException {
        /*List<Long> idList = findRelatedCourses(studentId).stream().map(Course::getId).toList();
        if (idList.contains(courseId)) throw new IllegalArgumentException(String.format("Course with id=%s was added before", courseId));
        try {
            jdbcTemplate.update(ADD_COURSE, studentId, courseId);
        } catch (Exception ignored) {
            throw new SQLException("Student or Course doesn't exist");
        }

         */
    }

    @Override
    public void deleteCourse(Long studentId, Long courseId) {
       /* List<Long> idList = findRelatedCourses(studentId).stream().map(Course::getId).toList();
        if (!idList.contains(courseId)) throw new IllegalArgumentException(String.format("Course with id=%s was not added before", courseId));
        jdbcTemplate.update(DELETE_COURSE, studentId, courseId);
        */
    }
}
