package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.modeldao.CourseDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JDBCCourseDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CourseDao dao;

    @BeforeEach
    void setUp() {
        dao = new JDBCCourseDao(jdbcTemplate);
    }

    @Test
    void shouldFindById() throws SQLException {
        Course course = new Course(1000L, "test", null);
        assertEquals(Optional.of(course), dao.findById(1000L));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        assertEquals(Optional.empty(), dao.findById(999L));
    }

    @Test
    void shouldFindAll() throws SQLException {
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
        assertEquals("Course doesn't exist", thrown.getMessage());
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
        assertEquals("Course doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldFindRelatedStudents() {
        List<Student> students = List.of(new Student(1000L, 1000, "max", "payne"));
        assertEquals(students, dao.findRelatedStudents(1000L));
    }

    @Test
    void shouldNotFindRelatedStudents() {
        JDBCStudentDao jdbcStudentDao = new JDBCStudentDao(jdbcTemplate);
        jdbcStudentDao.deleteCourse(1000L, 1000L);
        assertEquals(new ArrayList<>(), dao.findRelatedStudents(1000L));
    }
}