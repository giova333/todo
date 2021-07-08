package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.Status;
import lombok.Builder;
import lombok.Value;

import static com.gladunalexander.todo.domain.Task.TaskId;

public interface UpdateTaskStatusUseCase {

    void updateStatus(UpdateStatusCommand command);

    @Value
    @Builder
    class UpdateStatusCommand {
        TaskId taskId;
        Status status;
    }
}
