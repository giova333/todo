package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;

import java.util.List;
import java.util.Optional;

import static com.gladunalexander.todo.domain.Task.TaskId;

public interface TaskFetcher {

    List<Task> getTasks(TaskFilter taskFilter);

    Optional<Task> findById(TaskId taskId);
}
