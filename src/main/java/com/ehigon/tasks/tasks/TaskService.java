package com.ehigon.tasks.tasks;

import com.ehigon.tasks.error.ActiveException;
import com.ehigon.tasks.error.FinishedException;
import com.ehigon.tasks.error.NotFoundException;
import com.ehigon.tasks.finished.Finished;
import com.ehigon.tasks.finished.FinishedRepository;
import com.ehigon.tasks.finished.verifier.FinishedVerifierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    private final FinishedVerifierService finishedVerifierService;

    private final FinishedRepository finishedRepository;

    public TaskService (TaskRepository repository, FinishedVerifierService finishedVerifierService,
                        FinishedRepository finishedRepository) {
        this.repository = repository;
        this.finishedVerifierService = finishedVerifierService;
        this.finishedRepository = finishedRepository;
    }

    public Page<Task> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    public Task create(Task entity) {
        return repository.save(entity);
    }

    public Task update(Long id, Task update) {
        return update(id, update, false);
    }

    public Task updatePatch(Long id, Task update) {
        return update(id, update, true);
    }

    public Task setAsFinished(Long id) {
        Task task = getTaskById(id);
        if(finishedVerifierService.isFinished(task)) {
            throw new FinishedException();
        }
        if(task.getDate().isAfter(LocalDateTime.now())){
            throw new ActiveException();
        }
        Finished finished = finishedRepository.save(Finished.builder()
                .task(task)
                .finishDate(LocalDateTime.now())
                .build());
        task.getFinished().add(finished);
        return task;
    }

    private Task update(Long id, Task update, boolean patch) {
        Task task = getTaskById(id);
        if(!patch || update.getDate() != null) {
            task.setDate(update.getDate());
        }
        if(!patch || update.getDetails() != null) {
            task.setDetails(update.getDetails());
        }
        if(!patch || update.getTitle() != null) {
            task.setTitle(update.getTitle());
        }
        if(!patch || update.getRepeatType() != null) {
            task.setRepeatType(update.getRepeatType());
        }
        return repository.save(task);
    }

    public void delete(Long id) {
        Task task = getTaskById(id);
        finishedRepository.deleteAll(task.getFinished());
        repository.delete(task);
    }

    private Task getTaskById(Long id) {
        Optional<Task> optTask = repository.findById(id);
        if(!optTask.isPresent()) {
            throw new NotFoundException();
        }
        return optTask.get();
    }
}
