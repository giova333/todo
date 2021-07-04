package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskPersister;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class TaskPersistenceAdapter implements TaskPersister, TaskFetcher {

    private final TaskJpaRepository taskJpaRepository;
    private final TaskConverter taskConverter;

    @Override
    public Task save(Task task) {
        var taskJpaEntity = taskConverter.convert(task);
        taskJpaRepository.save(taskJpaEntity);
        return task;
    }

    @Override
    public List<Task> getTasks(TaskFilter filter) {
        return taskJpaRepository.findAll(filter).stream()
                                .map(taskConverter::convert)
                                .collect(Collectors.toList());
    }
}
