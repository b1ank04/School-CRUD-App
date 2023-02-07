package com.foxminded.school.dao.modeldao;

import com.foxminded.school.dao.CrudDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao extends CrudDao<Student, Long> {
    List<Course> findRelatedCourses(Long id);
    void addCourse(Long studentId, Long courseId) throws SQLException;
    void deleteCourse(Long studentId, Long courseId);
}
