package com.foxminded.school.model.student;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(rs.getLong("id"),
                            rs.getInt("group_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"));
    }
}
