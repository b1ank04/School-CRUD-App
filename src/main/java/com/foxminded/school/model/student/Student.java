package com.foxminded.school.model.student;

import com.foxminded.school.model.HasId;
import com.foxminded.school.model.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "students")
@ToString
@EqualsAndHashCode
public class Student implements HasId<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Column(name = "group_id")
    @Getter
    @Setter
    private Integer groupId;
    @Column(name = "first_name")
    @Getter
    @Setter
    private String firstName;
    @Column(name = "last_name")
    @Getter
    @Setter
    private String lastName;

    @ToString.Exclude
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))

    private Set<Course> courses = new HashSet<>();

    public Student(Long id, Integer groupId, String firstName, String lastName) {
        this.id = id;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Long id, Integer groupId, String firstName, String lastName, Set<Course> courses) {
        this.id = id;
        this.groupId = groupId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courses = courses;
    }

    public void addCourse(Course course) throws SQLException {
        if (!getCourses().contains(course)) {
            course.getStudents().add(this);
            getCourses().add(course);
        } else throw new SQLException("Course with id="+ course.getId() + " was added before");
    }

    public void removeCourse(Course course) throws SQLException {
        if (getCourses().contains(course)) {
            course.getStudents().remove(this);
            getCourses().remove(course);
        } else throw new SQLException("Course with id =" + course.getId() + " wasn't added before");
    }
}
