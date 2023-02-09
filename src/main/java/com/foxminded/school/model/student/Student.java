package com.foxminded.school.model.student;

import com.foxminded.school.model.HasId;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Student implements HasId<Long> {
    private Long id;
    private final Integer groupId;
    private final String firstName;
    private final String lastName;
}