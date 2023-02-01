package com.foxminded.school.dao.jdbc;

import com.foxminded.school.Database;
import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCStudentDao extends AbstractCrudDao<Student, Long> implements StudentDao {
    private static final String INSERT = "INSERT INTO students (group_id, first_name, last_name) values (?,?,?)";
    private static final String UPDATE = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? where id = ?";
    private static final String SELECT_ALL = "SELECT * FROM students";
    private static final String SELECT = "SELECT * FROM STUDENTS WHERE id=?";
    private static final String DELETE = "DELETE FROM students WHERE id = ?";
    @Override
    protected Student create(Student entity) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setObject(1, entity.getGroupId());
                preparedStatement.setString(2, entity.getFirstName());
                preparedStatement.setString(3, entity.getLastName());
                preparedStatement.execute();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating of student failed, no ID obtained.");
                    }
                }
                return entity;
            }
        }
    }

    @Override
    protected Student update(Student entity) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
                preparedStatement.setInt(1, entity.getGroupId());
                preparedStatement.setString(2, entity.getFirstName());
                preparedStatement.setString(3, entity.getLastName());
                preparedStatement.setLong(4, entity.getId());
                preparedStatement.execute();
                return entity;
            }
        }
    }

    @Override
    public Optional<Student> findById(Long id) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setLong(1, id);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(new Student(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
                    }
                    else return Optional.empty();
                }
            }
        }
    }

    @Override
    public List<Student> findAll() throws SQLException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    List<Student> students = new ArrayList<>();
                    while (rs.next()) {
                        students.add(new Student(rs.getLong("id"), rs.getInt("group_id"), rs.getString("first_name"), rs.getString("last_name")));
                    }
                    return students;
                }
            }
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
                preparedStatement.setLong(1, id);
                preparedStatement.execute();
            }
        }
    }
}
