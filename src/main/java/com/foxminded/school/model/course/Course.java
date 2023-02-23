package com.foxminded.school.model.course;

import com.foxminded.school.model.student.Student;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Column(name = "name")
    @Getter
    @Setter
    private String name;
    @Column(name = "description")
    @Getter
    @Setter
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Student> students = new HashSet<>();

    public Course(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
