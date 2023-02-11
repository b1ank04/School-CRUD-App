package com.foxminded.school.service;

import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.jdbc.JDBCGroupDao;
import com.foxminded.school.model.group.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements GroupDao {

    private final JDBCGroupDao jdbcGroupDao;

    public GroupService(JDBCGroupDao jdbcGroupDao) {
        this.jdbcGroupDao = jdbcGroupDao;
    }

    @Transactional
    public Group save(Group group) throws SQLException {
        return jdbcGroupDao.save(group);
    }

    @Transactional(readOnly = true)
    public Optional<Group> findById(Long id) {
        return jdbcGroupDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Group> findAll() {
        return jdbcGroupDao.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        jdbcGroupDao.deleteById(id);
    }

}
