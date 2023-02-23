package com.foxminded.school.dao.jdbc;

import com.foxminded.school.model.group.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {JPAGroupDao.class})})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JPAGroupDaoTest {
    @Autowired
    private JPAGroupDao dao;


    @Test
    void shouldFindById() {
        Group group = new Group(1000L, "TEST");
        assertEquals(Optional.of(group), dao.findById(1000L));
    }

    @Test
    void shouldNotFindById() {
        assertEquals(Optional.empty(), dao.findById(999L));
    }

    @Test
    void shouldFindAll() {
        assertEquals(new HashSet<>(List.of(new Group(1000L, "TEST"),
                new Group(500L, "TEST1"))), new HashSet<>(dao.findAll()));
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
        assertEquals("Group with id=123 doesn't exist", thrown.getMessage());
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
        assertEquals("Group with id=123 doesn't exist", thrown.getMessage());
    }
}