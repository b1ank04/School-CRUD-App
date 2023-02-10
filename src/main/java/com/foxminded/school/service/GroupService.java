package com.foxminded.school.service;

import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.jdbc.JDBCGroupDao;
import com.foxminded.school.model.group.Group;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements GroupDao {

    private final JDBCGroupDao jdbcGroupDao;

    public GroupService(JDBCGroupDao jdbcGroupDao) {
        this.jdbcGroupDao = jdbcGroupDao;
    }

    public Group save(Group group) throws SQLException {
        return jdbcGroupDao.save(group);
    }

    public Optional<Group> findById(Long id) {
        return jdbcGroupDao.findById(id);
    }

    public List<Group> findAll() {
        return jdbcGroupDao.findAll();
    }

    public void deleteById(Long id) throws SQLException {
        jdbcGroupDao.deleteById(id);
    }

}
