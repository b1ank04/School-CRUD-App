package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.model.group.Group;
import com.foxminded.school.model.group.GroupMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCGroupDao extends AbstractCrudDao<Group, Long> implements GroupDao {
    private static final String UPDATE = "UPDATE groups SET name = ? where id = ?";
    private static final String SELECT_ALL = "SELECT * FROM groups";
    private static final String SELECT = "SELECT * FROM groups WHERE id=?";
    private static final String DELETE = "DELETE FROM groups WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final GroupMapper groupMapper = new GroupMapper();

    public JDBCGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("groups")
                .usingColumns("name")
                .usingGeneratedKeyColumns("id");
    }
    @Override
    protected Group create(Group entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        entity.setId(newId.longValue());
        return entity;
    }

    @Override
    protected Group update(Group entity) throws SQLException {
        int update = jdbcTemplate.update(UPDATE, entity.getName(), entity.getId());
        if (update != 1) throw new SQLException("Group doesn't exist");
        return entity;
    }

    @Override
    public Optional<Group> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT, groupMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(SELECT_ALL, groupMapper);
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        int update = jdbcTemplate.update(DELETE, id);
        if (update != 1) throw new SQLException("Group doesn't exist");
    }
}
