package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;

import java.util.List;

public interface TaskFetcher {

    List<Task> getTasks(TaskFilter taskFilter);
}
