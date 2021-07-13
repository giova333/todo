package com.gladunalexander.todo.application;

import com.gladunalexander.todo.domain.DoneTask;
import com.gladunalexander.todo.domain.TaskId;
import com.gladunalexander.todo.ports.in.CompleteTaskUseCase;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskWriteOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
class CompleteTaskService implements CompleteTaskUseCase {

    private final TaskFetcher taskFetcher;
    private final TaskWriteOperations writeOperations;

    @Override
    public DoneTask complete(TaskId taskId) {
        var activeTask = taskFetcher.findActiveTaskById(taskId)
                                    .orElseThrow(() -> new TaskNotFoundException(taskId));
        log.info("Completing task: {}", activeTask);
        var doneTask = activeTask.done();
        writeOperations.update(doneTask);
        return doneTask;
    }
}
