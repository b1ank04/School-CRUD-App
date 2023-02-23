package com.foxminded.school.dao;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao extends CrudDao<Course, Long> {
    List<Student> findRelatedStudents(Long id) throws SQLException;
}
