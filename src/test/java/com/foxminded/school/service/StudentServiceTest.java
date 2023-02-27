package com.foxminded.school.service;

import com.foxminded.school.model.course.Course;
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

@DataJpaTest(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {StudentService.class})})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void shouldDeleteById() throws SQLException {
        studentService.deleteById(1000L);
        assertEquals(Optional.empty(), studentService.findById(1000L));
    }

    @Test
    void shouldNotDeleteById() {
        Exception thrown = assertThrows(SQLException.class, () -> studentService.deleteById(111L));
        assertEquals("Student with id=111 doesn't exist", thrown.getMessage());
    }
    @Test
    void shouldFindRelatedCourses() {
        List<Course> courses = List.of(new Course(1000L, "test", null), new Course(1001L, "test1", null));
        assertEquals(new HashSet<>(courses), new HashSet<>(studentService.findRelatedCourses(1000L)));
    }

    @Test
    void shouldNotFindRelatedCourses() throws SQLException {
        studentService.removeCourse(1000L,1000L);
        studentService.removeCourse(1000L, 1001L);
        assertEquals(new ArrayList<>(), studentService.findRelatedCourses(1000L));
    }

    @Test
    void shouldAddCourse() {
        assertDoesNotThrow(() -> studentService.addCourse(1000L, 500L));
    }

    @Test
    void shouldNotAddBeforeAddedCourse() {
        Exception thrown = assertThrows(SQLException.class, () -> studentService.addCourse(1000L,1000L));
        assertEquals("Course with id=1000 was added before", thrown.getMessage());
    }

    @Test
    void shouldNotAddWrongCourseOrStudent() {
        Exception thrown = assertThrows(SQLException.class, () -> studentService.addCourse(1000L, 123L));
        assertEquals("Student or Course with given IDs don't exist", thrown.getMessage());
    }

    @Test
    void shouldDeleteCourse() {
        assertDoesNotThrow(() -> studentService.removeCourse(1000L, 1000L));
    }

    @Test
    void shouldNotDeleteCourse() {
        Exception thrown = assertThrows(SQLException.class, () -> studentService.removeCourse(1000L, 123L));
        assertEquals("Student or Course with given IDs doesn't exist", thrown.getMessage());
    }
}