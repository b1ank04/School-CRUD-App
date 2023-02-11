package com.foxminded.school.database;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DatabaseService implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_GROUPS = "INSERT INTO groups (name) values (?)";
    private static final String INSERT_STUDENTS = "INSERT INTO students (group_id, first_name, last_name) values (?,?,?)";
    private static final String INSERT_COURSES = "INSERT INTO courses (name, description) values (?,?)";
    private static final String INSERT_STUDENT_COURSE = "INSERT INTO student_course (student_id, course_id) VALUES (?,?)";

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void run(ApplicationArguments args) {
        Integer studentsFlag = jdbcTemplate.queryForList("SELECT count(id) FROM students", Integer.class).get(0);
        Integer groupsFlag = jdbcTemplate.queryForList("SELECT count(id) FROM groups", Integer.class).get(0);
        Integer coursesFlag = jdbcTemplate.queryForList("SELECT count(id) from courses", Integer.class).get(0);
        Integer studentCourseFlag = jdbcTemplate.queryForList("SELECT count(student_id) from student_course", Integer.class).get(0);
        if (studentsFlag < 1) fillStudents(DataGenerator.createStudents().stream().toList());
        if (groupsFlag < 1) fillGroups(DataGenerator.createGroups().stream().toList());
        if (coursesFlag < 1) fillCourses(DataGenerator.createCourses().stream().toList());
        if (studentCourseFlag < 1) fillStudCourse(DataGenerator.assignStudentsToCourses());
    }

    private void fillGroups(List<String> groups){
        for (String s : groups) {
            jdbcTemplate.update(INSERT_GROUPS, s);
        }
    }

    private void fillStudents(List<Student> students) {
        for (Student s : students) {
            jdbcTemplate.update(INSERT_STUDENTS,
                    s.getGroupId() == null ? null : s.getGroupId(),
                    s.getFirstName(),
                    s.getLastName());
        }
    }

    private void fillCourses(List<Course> courses) {
        for (Course c : courses) {
            jdbcTemplate.update(INSERT_COURSES, c.getName(), c.getDescription());
        }
    }

    private void fillStudCourse(Map<Integer, Set<Integer>> studCourse) {
        for (int i = 1; i <= studCourse.size(); ++i) {
            List<Integer> courses = studCourse.get(i).stream().toList();
            for (Integer course : courses) {
                jdbcTemplate.update(INSERT_STUDENT_COURSE, i, course);
            }
        }
    }
}
