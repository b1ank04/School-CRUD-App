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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {JPAStudentDao.class})})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JPAStudentDaoTest {

    @Autowired
    private JPAStudentDao dao;


    @Test
    void shouldFindById() {
        Student student = new Student(1000L, 1000, "max", "payne");
        assertEquals(Optional.of(student), dao.findById(1000L));
    }

    @Test
    void shouldNotFindById() {
        assertEquals(Optional.empty(), dao.findById(999L));
    }

    @Test
    void shouldFindAll() {
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
        Exception thrown = assertThrows(SQLException.class, () -> dao.deleteById(111L));
        assertEquals("Student with id=111 doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldCreate() throws SQLException {
        Student student = new Student(1L, 1000, "barry", "allen");
        assertEquals(student, dao.save(new Student(null, 1000, "barry", "allen")));
    }

    @Test
    void shouldUpdate() {
        assertDoesNotThrow(() -> dao.save(new Student(1000L, 1000, "maxim", "payne")));
    }

    @Test
    void shouldNotUpdate() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.save(new Student(500L, 1000, "maxim", "payne")));
        assertEquals("Student with id=500 doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldFindRelatedCourses() {
        List<Course> courses = List.of(new Course(1000L, "test", null), new Course(1001L, "test1", null));
        assertEquals(new HashSet<>(courses), new HashSet<>(dao.findRelatedCourses(1000L)));
    }

    @Test
    void shouldNotFindRelatedCourses() throws SQLException {
        dao.removeCourse(1000L,1000L);
        dao.removeCourse(1000L, 1001L);
        assertEquals(new ArrayList<>(), dao.findRelatedCourses(1000L));
    }

    @Test
    void shouldAddCourse() {
        assertDoesNotThrow(() -> dao.addCourse(1000L, 500L));
    }

    @Test
    void shouldNotAddBeforeAddedCourse() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.addCourse(1000L,1000L));
        assertEquals("Course with id=1000 was added before", thrown.getMessage());
    }

    @Test
    void shouldNotAddWrongCourseOrStudent() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.addCourse(1000L, 123L));
        assertEquals("Student or Course with given IDs don't exist", thrown.getMessage());
    }

    @Test
    void shouldDeleteCourse() {
        assertDoesNotThrow(() -> dao.removeCourse(1000L, 1000L));
    }

    @Test
    void shouldNotDeleteCourse() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.removeCourse(1000L, 123L));
        assertEquals("Student or Course with given IDs doesn't exist", thrown.getMessage());
    }
}