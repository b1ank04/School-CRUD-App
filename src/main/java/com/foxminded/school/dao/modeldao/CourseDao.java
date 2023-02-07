package com.foxminded.school.dao.modeldao;

import com.foxminded.school.dao.CrudDao;
import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;

import java.util.List;

public interface CourseDao extends CrudDao<Course, Long> {
    List<Student> findRelatedStudents(Long id);
}
