package com.ehigon.tasks.tasks;

import com.ehigon.tasks.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/tasks")
@ExposesResourceFor(TaskModel.class)
public class TaskController {

    private final TaskService service;

    private final TaskModelAssembler assembler;

    private final PagedResourcesAssembler<Task> pagedResourcesAssembler;

    public TaskController(TaskService taskService, TaskModelAssembler taskModelAssembler,
                          PagedResourcesAssembler<Task> pagedResourcesAssembler) {
        this.service = taskService;
        this.assembler = taskModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<TaskModel>> findAll(Pageable pageable) {
        Page<Task> tasks = service.findAll(pageable);
        PagedModel<TaskModel> page = pagedResourcesAssembler.toModel(tasks, assembler);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> findById(@PathVariable("id") Long id) {
        Optional<Task> optTask = service.findById(id);
        if(!optTask.isPresent()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(assembler.toModel(optTask.get()));
    }

    @PostMapping
    public ResponseEntity<TaskModel> update(@RequestBody TaskModel taskModel) {
        Task task = service.create(assembler.toEntity(taskModel));
        return ResponseEntity.ok(assembler.toModel(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> update(@PathVariable("id") Long id, @RequestBody TaskModel taskModel) {
        Task task = service.update(id, assembler.toEntity(taskModel));
        if(isFinishedRequest(taskModel)) {
            task = service.setAsFinished(id);
        }
        return ResponseEntity.ok(assembler.toModel(task));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskModel> updatePatch(@PathVariable("id") Long id, @RequestBody TaskModel taskModel) {
        Task task = service.updatePatch(id, assembler.toEntity(taskModel));
        if(isFinishedRequest(taskModel)) {
            task = service.setAsFinished(id);
        }
        return ResponseEntity.ok(assembler.toModel(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isFinishedRequest(TaskModel taskModel) {
        return taskModel.getFinished() != null && taskModel.getFinished();
    }

}
