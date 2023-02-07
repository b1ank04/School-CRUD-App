package com.foxminded.school.model.group;

import com.foxminded.school.model.HasId;
import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Group implements HasId<Long> {
    private Long id;
    private String name;
}
