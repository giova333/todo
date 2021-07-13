package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.TaskId;
import lombok.Value;

public interface DeleteTaskUseCase {

    void deleteTask(DeleteTaskCommand command);

    @Value
    class DeleteTaskCommand {
        TaskId taskId;
    }
}
