package com.ehigon.tasks.tasks;

import com.ehigon.tasks.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskModelAssembler taskModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Task> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<TaskModel>> findAll(Pageable pageable) {
        Page<Task> tasks = taskService.findAll(pageable);
        PagedModel<TaskModel> page = pagedResourcesAssembler.toModel(tasks, taskModelAssembler);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> findById (@PathVariable("id") Long id) {
        Optional<Task> optTask = taskService.findById(id);
        if(!optTask.isPresent()) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(taskModelAssembler.toModel(optTask.get()));
    }

}
