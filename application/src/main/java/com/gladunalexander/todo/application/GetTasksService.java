package com.gladunalexander.todo.application;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
class GetTasksService implements GetTasksQuery {

    private final TaskFetcher taskFetcher;

    @Override
    public List<Task> getTasks(TaskFilter taskFilter) {
        return taskFetcher.getTasks(taskFilter).stream()
                          .sorted(comparing(task -> task.getStatus().getOrder()))
                          .collect(toList());
    }
}
