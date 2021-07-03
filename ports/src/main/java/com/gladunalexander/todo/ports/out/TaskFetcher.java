package com.gladunalexander.todo.ports.out;

import com.gladunalexander.todo.domain.Task;

import java.util.List;

public interface TaskFetcher {

    List<Task> getTasks();
}
