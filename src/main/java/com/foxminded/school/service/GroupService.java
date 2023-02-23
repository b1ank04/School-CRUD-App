package com.foxminded.school.service;

import com.foxminded.school.repository.jpa.JPAGroupRepository;
import com.foxminded.school.model.group.Group;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final JPAGroupRepository jpaGroupRepository;

    public GroupService(JPAGroupRepository jpaGroupRepository) {
        this.jpaGroupRepository = jpaGroupRepository;
    }

    @Transactional
    public Group save(Group group) throws SQLException {
        return jpaGroupRepository.save(group);
    }

    @Transactional(readOnly = true)
    public Optional<Group> findById(Long id) {
        return jpaGroupRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Group> findAll() {
        return jpaGroupRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws SQLException {
        jpaGroupRepository.deleteById(id);
    }
}
