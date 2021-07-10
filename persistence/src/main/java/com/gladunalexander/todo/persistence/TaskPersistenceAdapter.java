package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.ports.out.TaskFetcher;
import com.gladunalexander.todo.ports.out.TaskWriteOperations;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gladunalexander.todo.domain.Task.TaskId;

@RequiredArgsConstructor
class TaskPersistenceAdapter implements TaskWriteOperations, TaskFetcher {

    private final TaskJpaRepository taskJpaRepository;
    private final TaskConverter taskConverter;

    @Override
    public Task save(Task task) {
        var taskJpaEntity = taskConverter.convert(task);
        taskJpaRepository.save(taskJpaEntity);
        return task;
    }

    @Override
    public void delete(Task task) {
        var taskJpaEntity = taskConverter.convert(task);
        taskJpaEntity.setDeleted(true);
        taskJpaRepository.save(taskJpaEntity);
    }

    @Override
    public List<Task> getTasks(TaskFilter filter) {
        return taskJpaRepository.findAll(filter).stream()
                                .map(taskConverter::convert)
                                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(TaskId taskId) {
        return taskJpaRepository.findById(taskId.getUuid().toString())
                                .map(taskConverter::convert);
    }
}
