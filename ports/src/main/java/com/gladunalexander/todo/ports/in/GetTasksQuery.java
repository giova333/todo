package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;

import java.util.List;

public interface GetTasksQuery {

    List<Task> getTasks(TaskFilter taskFilter);

}
