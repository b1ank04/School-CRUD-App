package com.foxminded.school.model.group;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "groups")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
