package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.ActiveTask;
import lombok.Value;

public interface CreateTaskUseCase {

    ActiveTask create(CreateTaskCommand command);

    @Value
    class CreateTaskCommand {
        String name;
    }
}
