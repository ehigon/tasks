package com.ehigon.tasks.tasks;

import com.ehigon.tasks.finished.verifier.FinishedVerifierService;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TaskModelAssembler extends RepresentationModelAssemblerSupport<Task, TaskModel> {

    private final FinishedVerifierService finishedVerifierService;

    public TaskModelAssembler(FinishedVerifierService finishedVerifierService) {
        super(TaskController.class, TaskModel.class);
        this.finishedVerifierService = finishedVerifierService;
    }

    @Override
    public TaskModel toModel(Task entity) {
        TaskModel taskModel = createModelWithId(entity.getId(), entity);
        taskModel.setTitle(entity.getTitle());
        taskModel.setDetails(entity.getDetails());
        taskModel.setDate(entity.getDate());
        taskModel.setRepeatType(entity.getRepeatType());
        taskModel.setFinished(finishedVerifierService.isFinished(entity));
        return taskModel;
    }

    public Task toEntity(TaskModel model) {
        return Task.builder()
                .title(model.getTitle())
                .repeatType(model.getRepeatType())
                .details(model.getDetails())
                .date(model.getDate())
                .build();
    }
}
