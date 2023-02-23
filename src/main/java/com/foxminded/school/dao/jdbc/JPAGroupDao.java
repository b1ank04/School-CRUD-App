package com.foxminded.school.dao.jdbc;

import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.model.group.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JPAGroupDao extends AbstractCrudDao<Group, Long> implements GroupDao {

    @PersistenceContext
    EntityManager em;

    @Override
    protected Group create(Group entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    protected Group update(Group entity) throws SQLException {
        Optional<Group> group = findById(entity.getId());
        if (group.isPresent()) {
            return em.merge(entity);
        } else throw new SQLException(String.format("Group with id=%d doesn't exist", entity.getId()));
    }

    @Override
    public Optional<Group> findById(Long id) {
        return Optional.ofNullable(em.find(Group.class, id));
    }

    @Override
    public List<Group> findAll() {
        return em.createQuery("from Group", Group.class).getResultList();
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Optional<Group> group = findById(id);
        if (group.isPresent()) {
            em.remove(group.get());
        } else throw new SQLException("Group with id="+ id +" doesn't exist");
    }
}
