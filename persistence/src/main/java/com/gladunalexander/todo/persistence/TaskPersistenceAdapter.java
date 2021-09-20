package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.ActiveTask;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.domain.TaskId;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskWriteOperations;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class TaskPersistenceAdapter implements TaskWriteOperations, TaskFetcher {

    private final TaskJpaRepository taskJpaRepository;
    private final TaskConverter taskConverter;

    @Override
    public ActiveTask create(ActiveTask task) {
        save(task);
        return task;
    }

    @Override
    public void update(Task task) {
        save(task);
    }

    @Override
    public void delete(Task task) {
        var taskJpaEntity = taskConverter.convert(task);
        taskJpaEntity.setDeleted(true);
        taskJpaRepository.save(taskJpaEntity);
    }

    @Override
    public List<Task> getTasks(TaskFilter taskFilter) {
        return taskJpaRepository.findAll(TaskJpaEntity.Status.from(taskFilter.getStatus())).stream()
                                .map(taskConverter::convert)
                                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(TaskId taskId) {
        return taskJpaRepository.findById(taskId.getUuid().toString())
                                .map(taskConverter::convert);
    }

    @Override
    public Optional<ActiveTask> findActiveTaskById(TaskId taskId) {
        return findById(taskId)
                .filter(ActiveTask.class::isInstance)
                .map(ActiveTask.class::cast);
    }

    public Task save(Task task) {
        var taskJpaEntity = taskConverter.convert(task);
        taskJpaRepository.save(taskJpaEntity);
        return task;
    }
}
