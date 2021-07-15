package com.gladunalexander.todo.application;

import com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskWriteOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
class UpdateTaskStatusService implements UpdateTaskStatusUseCase {

    private final TaskWriteOperations taskWriteOperations;
    private final TaskFetcher taskFetcher;

    @Override
    public void updateStatus(UpdateStatusCommand command) {
        log.info("Updating task status: {}", command);
        var task = taskFetcher.findById(command.getTaskId())
                              .orElseThrow(() -> new TaskNotFoundException(command.getTaskId()));

        var taskWithUpdatedStatus = task.updateStatus(command.getStatus());
        taskWriteOperations.save(taskWithUpdatedStatus.getAggregate());
    }
}
