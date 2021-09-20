package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.ActiveTask;
import com.gladunalexander.todo.domain.DoneTask;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskId;

import static com.gladunalexander.todo.persistence.TaskJpaEntity.Status.ACTIVE;
import static com.gladunalexander.todo.persistence.TaskJpaEntity.Status.DONE;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

class TaskConverter {

    TaskJpaEntity convert(Task task) {
        return TaskJpaEntity.builder()
                            .id(task.getId().getUuid().toString())
                            .name(task.getName())
                            .status(TaskJpaEntity.Status.valueOf(task.getStatus()))
                            .build();
    }

    Task convert(TaskJpaEntity entity) {
        return Match(entity.getStatus()).of(
                Case($(ACTIVE), () -> toActiveTask(entity)),
                Case($(DONE), () -> toDoneTask(entity))
        );
    }

    private DoneTask toDoneTask(TaskJpaEntity entity) {
        return new DoneTask(TaskId.fromString(entity.getId()),
                            entity.getName()
        );
    }

    private ActiveTask toActiveTask(TaskJpaEntity entity) {
        return new ActiveTask(TaskId.fromString(entity.getId()),
                              entity.getName()
        );
    }
}
