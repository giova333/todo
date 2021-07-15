package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;

import java.util.List;

import static com.gladunalexander.todo.domain.Task.TaskId;

public interface GetTasksQuery {

    List<Task> getTasks(TaskFilter taskFilter);

    Task getTask(TaskId taskId);
}
