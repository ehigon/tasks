package com.ehigon.tasks.tasks;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Relation(value = "task", collectionRelation = "tasks")
public class TaskModel extends RepresentationModel<TaskModel> {

    @NotNull
    private String title;
    @NotNull
    private String details;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private RepeatType repeatType;
    private Boolean finished;
}
