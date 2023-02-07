package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.modeldao.GroupDao;
import com.foxminded.school.model.group.Group;
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
class JDBCGroupDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private GroupDao dao;

    @BeforeEach
    void setUp() {
        dao = new JDBCGroupDao(jdbcTemplate);
    }

    @Test
    void shouldFindById() throws SQLException {
        Group group = new Group(1000L, "TEST");
        assertEquals(Optional.of(group), dao.findById(1000L));
    }

    @Test
    void shouldNotFindById() throws SQLException {
        assertEquals(Optional.empty(), dao.findById(999L));
    }

    @Test
    void shouldFindAll() throws SQLException {
        assertEquals(List.of(new Group(1000L, "TEST"),
                new Group(500L, "TEST1")), dao.findAll());
    }

    @Test
    void shouldNotFindAll() throws SQLException {
        dao.deleteById(1000L);
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
        assertEquals("Group doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldCreate() throws SQLException {
        Group group = new Group(1L,"new");
        assertEquals(group, dao.save(new Group(null, "new")));
    }

    @Test
    void shouldUpdate() {
        assertDoesNotThrow(() -> dao.save(new Group(1000L, "TEST1")));
    }

    @Test
    void shouldNotUpdate() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.save(new Group(123L, "fail")));
        assertEquals("Group doesn't exist", thrown.getMessage());
    }
}