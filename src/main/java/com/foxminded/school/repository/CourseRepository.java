package com.foxminded.school.repository;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    @Query("""
    SELECT s from Student s
    JOIN s.courses c
    WHERE c.id = :courseId
    """)
    List<Student> findRelatedStudents(@Param("courseId") Long id);
}
