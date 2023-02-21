package com.foxminded.school.service;

import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.jdbc.JPAGroupDao;
import com.foxminded.school.model.group.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements GroupDao {

    private final JPAGroupDao jpaGroupDao;

    public GroupService(JPAGroupDao jpaGroupDao) {
        this.jpaGroupDao = jpaGroupDao;
    }

    @Transactional
    public Group save(Group group) throws SQLException {
        return jpaGroupDao.save(group);
    }

    @Transactional(readOnly = true)
    public Optional<Group> findById(Long id) {
        return jpaGroupDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Group> findAll() {
        return jpaGroupDao.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        jpaGroupDao.deleteById(id);
    }

}
