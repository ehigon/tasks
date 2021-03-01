package com.ehigon.tasks.tasks;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TaskModelAssembler extends RepresentationModelAssemblerSupport<Task, TaskModel> {
    public TaskModelAssembler() {
        super(TaskController.class, TaskModel.class);
    }

    @Override
    public TaskModel toModel(Task entity) {
        TaskModel taskModel = createModelWithId(entity.getId(), entity);
        taskModel.setTitle(entity.getTitle());
        taskModel.setDetails(entity.getDetails());
        taskModel.setDate(entity.getDate());
        taskModel.setRepeatType(entity.getRepeatType());
        return taskModel;
    }
}
