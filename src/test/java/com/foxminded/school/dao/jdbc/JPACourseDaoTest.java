package com.foxminded.school.dao.jdbc;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {JPACourseDao.class, JPAStudentDao.class})})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JPACourseDaoTest {

    @Autowired
    private JPACourseDao dao;

    @Autowired
    private JPAStudentDao studentDao;

    @Test
    void shouldFindById() {
        Course course = new Course(1000L, "test", null);
        assertEquals(Optional.of(course), dao.findById(1000L));
    }

    @Test
    void shouldNotFindById() {
        assertEquals(Optional.empty(), dao.findById(999L));
    }

    @Test
    void shouldFindAll() {
        assertEquals(List.of(new Course(1000L, "test", null),
                new Course(1001L, "test1", null),
                new Course(500L, "test2", null)), dao.findAll());
    }

    @Test
    void shouldNotFindAll() throws SQLException {
        dao.deleteById(1000L);
        dao.deleteById(1001L);
        dao.deleteById(500L);
        assertEquals(new ArrayList<>(), dao.findAll());
    }

    @Test
    void shouldDeleteById() throws SQLException {
        dao.deleteById(1000L);
        assertEquals(Optional.empty(), dao.findById(1000L));
    }

    @Test
    void shouldNotDeleteById() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.deleteById(123L));
        assertEquals("Course with id=123 doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldCreate() throws SQLException {
        Course course = new Course(1L, "create", null);
        assertEquals(course, dao.save(new Course(null, "create", null)));
    }

    @Test
    void shouldUpdate() {
        assertDoesNotThrow(() -> dao.save(new Course(1000L, "TEST1", null)));
    }

    @Test
    void shouldNotUpdate() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.save(new Course(123L, "fail", null)));
        assertEquals("Course with id=123 doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldFindRelatedStudents() throws SQLException {
        List<Student> students = List.of(new Student(1000L, 1000, "max", "payne"));
        assertEquals(students, dao.findRelatedStudents(1000L));
    }

    @Test
    void shouldNotFindRelatedStudents() throws SQLException {
        studentDao.removeCourse(1000L, 1000L);
        assertEquals(new ArrayList<>(), dao.findRelatedStudents(1000L));
    }
}