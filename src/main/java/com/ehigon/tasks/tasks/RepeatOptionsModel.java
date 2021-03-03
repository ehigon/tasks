package com.ehigon.tasks.tasks;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Relation(value = "repeat-options", collectionRelation = "repeat-options")
public class RepeatOptionsModel extends RepresentationModel<RepeatOptionsModel> {
    List<String> options = Arrays.stream(RepeatType.values())
            .map(RepeatType::toString)
            .collect(Collectors.toList());
}
