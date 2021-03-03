package com.ehigon.tasks.tasks;

import com.ehigon.tasks.finished.verifier.FinishedVerifierService;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler extends RepresentationModelAssemblerSupport<Task, TaskModel> {

    private final FinishedVerifierService finishedVerifierService;

    private LinkRelationProvider relProvider;

    public TaskModelAssembler(FinishedVerifierService finishedVerifierService, LinkRelationProvider relProvider) {
        super(TaskController.class, TaskModel.class);
        this.finishedVerifierService = finishedVerifierService;
        this.relProvider = relProvider;
    }

    @Override
    public TaskModel toModel(Task entity) {
        TaskModel taskModel = createModelWithId(entity.getId(), entity);
        taskModel.setTitle(entity.getTitle());
        taskModel.setDetails(entity.getDetails());
        taskModel.setDate(entity.getDate());
        taskModel.setRepeatType(entity.getRepeatType());
        taskModel.setFinished(finishedVerifierService.isFinished(entity));
        taskModel.add(
                linkTo(methodOn(RepeatOptionsController.class).getOptions())
                        .withRel(relProvider.getCollectionResourceRelFor(TaskModel.class))
        );
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
