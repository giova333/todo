package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.Task;

import java.util.List;

public interface GetTasksQuery {

    List<Task> getTasks();
}
