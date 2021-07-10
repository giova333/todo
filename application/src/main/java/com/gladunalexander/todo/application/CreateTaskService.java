package com.gladunalexander.todo.application;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.out.TaskWriteOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
class CreateTaskService implements CreateTaskUseCase {

    private final TaskWriteOperations taskWriteOperations;

    @Override
    public Task create(CreateTaskCommand command) {
        log.info("Creating task {}", command);
        var newTask = Task.create(command.getName());
        return taskWriteOperations.save(newTask);
    }
}
