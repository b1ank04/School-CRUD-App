package com.foxminded.school.dao.jdbc;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {JPACourseDao.class, JPAStudentDao.class})})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JPACourseDaoTest {

    @Autowired
    private JPACourseDao courseDao;

    @Autowired
    private JPAStudentDao studentDao;

    @Autowired
    private EntityManager em;

    @Test
    void shouldFindById() throws SQLException {
        Course course = new Course(1000L, "test", null);
        assertEquals(Optional.of(course), courseDao.findById(1000L));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        assertEquals(Optional.empty(), courseDao.findById(999L));
    }

    @Test
    void shouldFindAll() throws SQLException {
        assertEquals(List.of(new Course(1000L, "test", null),
                new Course(1001L, "test1", null),
                new Course(500L, "test2", null)), courseDao.findAll());
    }

    @Test
    void shouldNotFindAll() throws SQLException {
        courseDao.deleteById(1000L);
        courseDao.deleteById(1001L);
        courseDao.deleteById(500L);
        assertEquals(new ArrayList<>(), courseDao.findAll());
    }

    @Test
    void shouldDeleteById() throws SQLException {
        courseDao.deleteById(1000L);
        assertEquals(Optional.empty(), courseDao.findById(1000L));
    }

    @Test
    void shouldNotDeleteById() {
        Exception thrown = assertThrows(SQLException.class, () -> courseDao.deleteById(123L));
        assertEquals("Course with id=123 does not exists", thrown.getMessage());
    }

    @Test
    void shouldCreate() throws SQLException {
        Course course = new Course(1L, "create", null);
        assertEquals(course, courseDao.save(new Course(null, "create", null)));
    }

    @Test
    void shouldUpdate() {
        assertDoesNotThrow(() -> courseDao.save(new Course(1000L, "TEST1", null)));
    }

    @Test
    void shouldNotUpdate() {
        // see should not delete
        Exception thrown = assertThrows(SQLException.class, () -> courseDao.save(new Course(123L, "fail", null)));
        assertEquals("Course doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldFindRelatedStudents() {
        Set<Student> students = Set.of(new Student(1000L, 1000, "max", "payne"));
        assertEquals(students, courseDao.findById(1000L).get().getStudents());
    }

    @Test
    void shouldNotFindRelatedStudents() throws SQLException {
        Course course = courseDao.findById(1000L).get();
        Student student = course.getStudents().stream().findFirst().get();
        student.removeCourse(course); // you can do the same with course
        courseDao.save(course);

        flushAndClear(); // as you do all in single transaction, you need to cheat

        Course course1 = courseDao.findById(1000L).get();
        assertFalse(course1.getStudents().contains(student));
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }
}