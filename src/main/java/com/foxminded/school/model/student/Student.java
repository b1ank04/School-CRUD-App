package com.foxminded.school.model.student;

import com.foxminded.school.model.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "students")
@ToString
@EqualsAndHashCode
public class Student {
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
}
