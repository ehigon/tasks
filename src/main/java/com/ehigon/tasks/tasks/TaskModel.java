package com.ehigon.tasks.tasks;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(value = "task", collectionRelation = "tasks")
public class TaskModel extends RepresentationModel<TaskModel> {
    private String title;
    private String details;
    private LocalDateTime date;
    private RepeatType repeatType;
    private Boolean finished;
}
