package com.foxminded.school.service;

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

@DataJpaTest(includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {CourseService.class, StudentService.class})})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/sample_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Test
    void shouldDeleteById() throws SQLException {
        courseService.deleteById(1000L);
        assertEquals(Optional.empty(), courseService.findById(1000L));
    }

    @Test
    void shouldNotDeleteById() {
        Exception thrown = assertThrows(SQLException.class, () -> courseService.deleteById(123L));
        assertEquals("Course with id=123 doesn't exist", thrown.getMessage());
    }

    @Test
    void shouldFindRelatedStudents() {
        List<Student> students = List.of(new Student(1000L, 1000, "max", "payne"));
        assertEquals(students, courseService.findRelatedStudents(1000L));
    }

    @Test
    void shouldNotFindRelatedStudents() throws SQLException {
        studentService.removeCourse(1000L, 1000L);
        assertEquals(new ArrayList<>(), courseService.findRelatedStudents(1000L));
    }
}