package com.foxminded.school.dao;

import com.foxminded.school.model.HasId;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDao <T extends HasId<K>, K> {
    Optional<T> findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    T save(T entity) throws SQLException;
    void deleteById(K id) throws SQLException;
}
