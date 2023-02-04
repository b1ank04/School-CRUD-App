package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.model.student.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
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

    /*@Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("root")
            .withPassword("test")
            .withDatabaseName("test");



    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

     */

    @BeforeEach
    void setUp() {
        dao = new JDBCStudentDao(jdbcTemplate);
    }

    @Test
    void findByIdTest() throws SQLException {
        Student student = new Student(1000L, 1000, "max", "payne");
        assertEquals(student, dao.findById(1000L).get());
    }

    @Test
    void createTest() throws SQLException {
        Student student = new Student(1L, 1000, "barry", "allen");
        assertEquals(student,dao.save(new Student(null, 1000, "barry", "allen")));
    }

    @Test
    void updateTest() throws SQLException {
        Student student = new Student(1000L, 1000, "maxim", "payne");
        assertEquals( student ,dao.save(new Student(1000L, 1000, "maxim", "payne")));
    }

}