package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.Task;
import lombok.Value;

public interface CreateTaskUseCase {

    Task create(CreateTaskCommand command);

    @Value
    class CreateTaskCommand {
        String name;
    }
}
