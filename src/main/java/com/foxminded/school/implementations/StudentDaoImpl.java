package com.foxminded.school.implementations;

import com.foxminded.school.Database;
import com.foxminded.school.dao.AbstractCrudDao;
import com.foxminded.school.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl extends AbstractCrudDao<Student, Long> {
    private static final String INSERT = "INSERT INTO students (group_id, first_name, last_name) values (?,?,?)";
    private static final String UPDATE = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? where id = ?";
    private static final String SELECT_ALL = "SELECT * FROM students";
    @Override
    protected Student create(Student entity) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, entity.getGroupId());
                preparedStatement.setString(2, entity.getFirstName());
                preparedStatement.setString(3, entity.getLastName());
                preparedStatement.execute();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT max(id) FROM students");
                statement.close();
                Long id = null;
                while (rs.next()) {
                    id = rs.getLong(1);
                }
                entity.setId(id);
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
            String sql = "SELECT * FROM STUDENTS WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setLong(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                Student student = null;
                if (rs.next()) {
                    student = new Student(rs.getLong(1), rs.getInt(2), rs.getString(3), rs.getString(4));
                }
                assert student != null;
                return Optional.of(student);
            }
        }
    }

    @Override
    public List<Student> findAll() throws SQLException {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
                ResultSet rs = preparedStatement.executeQuery();
                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    students.add(new Student(rs.getLong("id"), rs.getInt("group_id"), rs.getString("first_name"), rs.getString("last_name")));
                }
                return students;
            }
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        Connection connection = Database.getConnection();
        String sql = "DELETE FROM students WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        connection.close();
    }
}
