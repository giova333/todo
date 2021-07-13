package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.ActiveTask;
import com.gladunalexander.todo.domain.DoneTask;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskFetcher {

    List<Task> getTasks();

    List<ActiveTask> getActiveTasks();

    List<DoneTask> getDoneTasks();

    Optional<Task> findById(TaskId taskId);

    Optional<ActiveTask> findActiveTaskById(TaskId taskId);
}
