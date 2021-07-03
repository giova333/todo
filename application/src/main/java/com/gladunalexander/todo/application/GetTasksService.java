package com.gladunalexander.todo.application;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetTasksService implements GetTasksQuery {

    private final TaskFetcher taskFetcher;

    @Override
    public List<Task> getTasks() {
        return taskFetcher.getTasks();
    }
}
