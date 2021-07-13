package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.ActiveTask;
import com.gladunalexander.todo.domain.Task;

public interface TaskWriteOperations {

    ActiveTask create(ActiveTask task);

    void update(Task task);

    void delete(Task task);
}
