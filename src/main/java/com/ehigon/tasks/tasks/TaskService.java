package com.ehigon.tasks.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public Page<Task> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }
}
