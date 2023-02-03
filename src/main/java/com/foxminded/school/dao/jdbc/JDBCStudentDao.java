package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.model.student.Student;
import com.foxminded.school.model.student.StudentMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JDBCStudentDao extends AbstractCrudDao<Student, Long> implements StudentDao {
    private static final String UPDATE = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? where id = ?";
    private static final String SELECT_ALL = "SELECT * FROM students";
    private static final String SELECT = "SELECT * FROM STUDENTS WHERE id=?";
    private static final String DELETE = "DELETE FROM students WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


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
    protected Student update(Student entity) {
        jdbcTemplate.update(UPDATE,
                entity.getGroupId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getId());
        return entity;
        }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT, new StudentMapper(), id));
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new StudentMapper());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
