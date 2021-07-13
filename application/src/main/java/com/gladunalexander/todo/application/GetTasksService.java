package com.gladunalexander.todo.application;

import com.gladunalexander.todo.domain.ActiveTask;
import com.gladunalexander.todo.domain.DoneTask;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class GetTasksService implements GetTasksQuery {

    private final TaskFetcher taskFetcher;

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(getActiveTasks());
        tasks.addAll(getDoneTasks());
        return tasks;
    }

    @Override
    public List<ActiveTask> getActiveTasks() {
        return taskFetcher.getActiveTasks();
    }

    @Override
    public List<DoneTask> getDoneTasks() {
        return taskFetcher.getDoneTasks();
    }
}
