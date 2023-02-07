package com.foxminded.school.model.course;

import com.foxminded.school.model.HasId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Course implements HasId<Long> {
    private Long id;
    private String name;
    private String description;
}
