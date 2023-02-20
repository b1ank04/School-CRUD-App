package com.foxminded.school.dao.jdbc;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = {"com.foxminded.school.dao.jdbc"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class JPAStudentDaoTest {

    @Autowired
    private JPAStudentDao dao;


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
        assertEquals(student, dao.save(new Student(null, 1000, "barry", "allen")));
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

    @Test
    void shouldFindRelatedCourses() {
        List<Course> courses = List.of(new Course(1000L, "test", null), new Course(1001L, "test1", null));
        assertEquals(courses, dao.findRelatedCourses(1000L));
    }

    @Test
    void shouldNotFindRelatedCourses() {
        dao.deleteCourse(1000L,1000L);
        dao.deleteCourse(1000L, 1001L);
        assertEquals(new ArrayList<>(), dao.findRelatedCourses(1000L));
    }

    @Test
    void shouldAddCourse() {
        assertDoesNotThrow(() -> dao.addCourse(1000L, 500L));
    }

    @Test
    void shouldNotAddBeforeAddedCourse() {
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> dao.addCourse(1000L,1000L));
        assertEquals("Course with id=1000 was added before", thrown.getMessage());
    }

    @Test
    void shouldNotAddWrongCourseOrStudent() {
        Exception thrown = assertThrows(SQLException.class, () -> dao.addCourse(1000L, 123L));
        assertEquals("Student or Course doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldDeleteCourse() {
        assertDoesNotThrow(() -> dao.deleteCourse(1000L, 1000L));
    }

    @Test
    void shouldNotDeleteCourse() {
        Exception thrown = assertThrows(IllegalArgumentException.class, () -> dao.deleteCourse(1000L, 123L));
        assertEquals("Course with id=123 was not added before", thrown.getMessage());
    }
}