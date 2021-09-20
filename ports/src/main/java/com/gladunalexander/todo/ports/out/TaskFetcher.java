package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.ActiveTask;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.domain.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskFetcher {

    List<Task> getTasks(TaskFilter taskFilter);

    Optional<Task> findById(TaskId taskId);

    Optional<ActiveTask> findActiveTaskById(TaskId taskId);
}
