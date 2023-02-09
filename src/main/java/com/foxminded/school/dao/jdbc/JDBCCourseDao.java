package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.CourseDao;
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
public class JDBCCourseDao extends AbstractCrudDao<Course, Long> implements CourseDao {
    private static final String UPDATE = "UPDATE courses SET name = ?, description = ? where id = ?";
    private static final String SELECT_ALL = "SELECT * FROM courses";
    private static final String SELECT = "SELECT * FROM courses WHERE id=?";
    private static final String DELETE = "DELETE FROM courses WHERE id = ?";
    private static final String SELECT_STUDENTS = """
                                                SELECT * FROM students
                                                INNER JOIN student_course
                                                ON students.id = student_course.student_id
                                                where student_course.course_id = ?
                                                """;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final CourseMapper courseMapper = new CourseMapper();
    private final StudentMapper studentMapper = new StudentMapper();

    public JDBCCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("courses")
                .usingColumns("name", "description")
                .usingGeneratedKeyColumns("id");
    }
    @Override
    protected Course create(Course entity){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName())
                .addValue("description", entity.getDescription());
        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        entity.setId(newId.longValue());
        return entity;
    }

    @Override
    protected Course update(Course entity) throws SQLException {
        int update = jdbcTemplate.update(UPDATE, entity.getName(), entity.getDescription(), entity.getId());
        if (update != 1) throw new SQLException("Course doesn't exist");
        return entity;
    }

    @Override
    public Optional<Course> findById(Long id){
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT, courseMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SELECT_ALL, courseMapper);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        int update = jdbcTemplate.update(DELETE, id);
        if (update != 1) throw new SQLException("Course doesn't exist");
    }

    @Override
    public List<Student> findRelatedStudents(Long id) {
        return jdbcTemplate.query(SELECT_STUDENTS, studentMapper, id);
    }
}
