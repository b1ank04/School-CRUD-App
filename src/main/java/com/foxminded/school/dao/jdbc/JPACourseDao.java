package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JPACourseDao extends AbstractCrudDao<Course, Long> implements CourseDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected Course create(Course entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    protected Course update(Course entity) throws SQLException {
        Optional<Course> course = findById(entity.getId());
        if (course.isPresent()) {
            return em.merge(entity);
        } else throw new SQLException(String.format("Course with id=%d doesn't exist", entity.getId()));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(em.find(Course.class, id));
    }

    @Override
    public List<Course> findAll() {
        return em.createQuery("from Course", Course.class).getResultList();
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Optional<Course> entity = findById(id);
        if (entity.isPresent()) {
            em.remove(entity.get());
        } else throw new SQLException("Course with id="+ id + " doesn't exist");
    }

    @Override
    public List<Student> findRelatedStudents(Long id) throws SQLException {
        Optional<Course> entity = findById(id);
        if (entity.isPresent()) {
            return entity.get().getStudents().stream().toList();
        } else throw new SQLException("Student with id="+ id + " doesn't exist");
    }
}
