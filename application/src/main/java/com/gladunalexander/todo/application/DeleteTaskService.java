package com.gladunalexander.todo.application;

import com.gladunalexander.todo.ports.in.DeleteTaskUseCase;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskWriteOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
class DeleteTaskService implements DeleteTaskUseCase {

    private final TaskFetcher taskFetcher;
    private final TaskWriteOperations taskWriteOperations;

    @Override
    public void deleteTask(DeleteTaskCommand command) {
        log.info("Deleting task: {}", command);
        var task = taskFetcher.findById(command.getTaskId())
                              .orElseThrow(() -> new TaskNotFoundException(command.getTaskId()));
        var deletedTask = task.delete();
        taskWriteOperations.delete(deletedTask.getAggregate());
    }
}
