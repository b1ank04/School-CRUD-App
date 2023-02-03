package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.StudentDao;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JDBCStudentDaoTest {
    @Autowired
    private DataSource dataSource;
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new JDBCStudentDao(jdbcTemplate);
    }

}