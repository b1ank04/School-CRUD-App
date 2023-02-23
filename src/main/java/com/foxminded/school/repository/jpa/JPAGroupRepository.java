package com.foxminded.school.repository.jpa;

import com.foxminded.school.repository.GroupRepository;
import com.foxminded.school.model.group.Group;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JPAGroupRepository {

    private final GroupRepository groupRepository;

    public JPAGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group save(Group entity) throws SQLException {
        if (entity.getId() == null) {
            return groupRepository.save(entity);
        } else if (groupRepository.findById(entity.getId()).isPresent()) {
            return groupRepository.save(entity);
        } else throw new SQLException(String.format("Group with id=%d doesn't exist", entity.getId()));
    }

    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public void deleteById(Long id) throws SQLException {
        Optional<Group> group = findById(id);
        if (group.isPresent()) {
            groupRepository.deleteById(id);
        } else throw new SQLException("Group with id="+ id +" doesn't exist");
    }
}
