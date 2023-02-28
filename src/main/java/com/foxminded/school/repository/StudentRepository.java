package com.foxminded.school.repository;

import com.foxminded.school.model.course.Course;
import com.foxminded.school.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("""
                                    SELECT c FROM Course c
                                    JOIN c.students s
                                    WHERE s.id = :studentId
                                    """)
    List<Course> findRelatedCourses(@Param("studentId") Long id);

    @Modifying
    @Query(value = "INSERT INTO student_course (student_id, course_id) values (:studentId, :courseId)", nativeQuery = true)
    void addCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Modifying
    @Query(value = "DELETE FROM student_course WHERE student_id= :studentId AND course_id = :courseId", nativeQuery = true)
    void removeCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
