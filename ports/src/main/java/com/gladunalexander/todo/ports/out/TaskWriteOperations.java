package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.Task;

public interface TaskWriteOperations {

    Task save(Task task);

    void delete(Task task);
}
