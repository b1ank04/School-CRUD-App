package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.modeldao.StudentDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.course.CourseMapper;
import com.foxminded.school.model.student.Student;
import com.foxminded.school.model.student.StudentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCStudentDao extends AbstractCrudDao<Student, Long> implements StudentDao {
    private static final String UPDATE = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? where id = ?";
    private static final String SELECT_ALL = "SELECT * FROM students";
    private static final String SELECT = "SELECT * FROM STUDENTS WHERE id=?";
    private static final String DELETE = "DELETE FROM students WHERE id = ?";
    private static final String SELECT_COURSES = """
                                                SELECT * FROM courses
                                                INNER JOIN student_course
                                                ON courses.id = student_course.course_id
                                                where student_course.student_id = ?
                                                """;
    private static final String ADD_COURSE = "INSERT INTO student_course values (?,?)";
    private static final String DELETE_COURSE = "DELETE FROM student_course WHERE student_id = ? AND course_id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final StudentMapper studentMapper = new StudentMapper();
    private final CourseMapper courseMapper = new CourseMapper();


    public JDBCStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("students")
                .usingColumns("group_id", "first_name", "last_name")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    protected Student create(Student entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("group_id", entity.getGroupId())
                .addValue("first_name", entity.getFirstName())
                .addValue("last_name", entity.getLastName());
        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        entity.setId(newId.longValue());
        return entity;
    }



    @Override
    protected Student update(Student entity) throws SQLException {
        int update = jdbcTemplate.update(UPDATE,
                entity.getGroupId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getId());
        if (update != 1) throw new SQLException("Student doesn't exist");
        return entity;
    }

    @Override
    public Optional<Student> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT, studentMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(SELECT_ALL, studentMapper);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        int update = jdbcTemplate.update(DELETE, id);
        if (update != 1) throw new SQLException("Student doesn't exist");
    }

    @Override
    public List<Course> findRelatedCourses(Long id) {
        return jdbcTemplate.query(SELECT_COURSES, courseMapper, id);
    }

    @Override
    public void addCourse(Long studentId, Long courseId) throws SQLException {
        List<Long> idList = findRelatedCourses(studentId).stream().map(Course::getId).toList();
        if (idList.contains(courseId)) throw new IllegalArgumentException(String.format("Course with id=%s was added before", courseId));
        try {
            jdbcTemplate.update(ADD_COURSE, studentId, courseId);
        } catch (Exception ignored) {
            throw new SQLException("Student or Course doesn't exist");
        }
    }

    @Override
    public void deleteCourse(Long studentId, Long courseId) {
        List<Long> idList = findRelatedCourses(studentId).stream().map(Course::getId).toList();
        if (!idList.contains(courseId)) throw new IllegalArgumentException(String.format("Course with id=%s was not added before", courseId));
        jdbcTemplate.update(DELETE_COURSE, studentId, courseId);
    }
}
