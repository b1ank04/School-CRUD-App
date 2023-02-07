package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.modeldao.StudentDao;
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
class JDBCStudentDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentDao dao;

    @BeforeEach
    void setUp() {
        dao = new JDBCStudentDao(jdbcTemplate);
    }

    @Test
    void shouldFindById() throws SQLException {
        Student student = new Student(1000L, 1000, "max", "payne");
        assertEquals(Optional.of(student), dao.findById(1000L));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        assertEquals(Optional.empty(), dao.findById(999L));
    }

    @Test
    void shouldFindAll() throws SQLException {
        assertEquals(List.of(new Student(1000L, 1000, "max", "payne"),
                new Student(1001L, 1000, "andrey", "pavlov")), dao.findAll());
    }

    @Test
    void shouldNotFindAll() throws SQLException {
        dao.deleteById(1000L);
        dao.deleteById(1001L);
        assertEquals(new ArrayList<>(), dao.findAll());
    }

    @Test
    void shouldDeleteById() throws SQLException {
        dao.deleteById(1000L);
        assertEquals(Optional.empty(), dao.findById(1000L));
    }

    @Test
    void shouldNotDeleteById() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.deleteById(500L));
        assertEquals("Student doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldCreate() throws SQLException {
        Student student = new Student(1L, 1000, "barry", "allen");
        assertEquals(student,dao.save(new Student(null, 1000, "barry", "allen")));
    }

    @Test
    void shouldUpdate() {
        assertDoesNotThrow(() -> dao.save(new Student(1000L, 1000, "maxim", "payne")));
    }

    @Test
    void shouldNotUpdate() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.save(new Student(500L, 1000, "maxim", "payne")));
        assertEquals("Student doesn't exist", thrown.getMessage());
    }

}