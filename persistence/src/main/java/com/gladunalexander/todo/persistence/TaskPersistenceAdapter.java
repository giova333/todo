package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.ports.out.TaskPersister;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskPersister {

    private final TaskJpaRepository taskJpaRepository;

    @Override
    public Task save(Task task) {
        taskJpaRepository.save(TaskJpaEntity.builder()
                                            .id(task.getTaskId().getUuid().toString())
                                            .name(task.getName())
                                            .status(task.getStatus().name())
                                            .build());
        return task;
    }
}
