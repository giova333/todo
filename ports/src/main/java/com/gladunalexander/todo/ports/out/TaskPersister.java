package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.Task;

public interface TaskPersister {

    Task save(Task task);
}
