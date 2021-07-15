package com.gladunalexander.todo.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gladunalexander.todo.common.DomainEvent;
import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.event.StatusChanged;
import com.gladunalexander.todo.domain.event.TaskCreated;
import com.gladunalexander.todo.domain.event.TaskDeleted;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.UUID;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@RequiredArgsConstructor
class TaskConverter {

    private final ObjectMapper objectMapper;

    public TaskJpaEntity convert(Task task) {
        var entity = TaskJpaEntity.builder()
                                  .id(task.getTaskId().getUuid().toString())
                                  .name(task.getName())
                                  .status(task.getStatus().name())
                                  .build();
        entity.setEvents(task.getDomainEvents().stream()
                             .map(domainEvent -> convert(domainEvent, entity))
                             .collect(Collectors.toList())
        );
        return entity;
    }

    public Task convert(TaskJpaEntity entity) {
        return new Task(Task.TaskId.of(UUID.fromString(entity.getId())),
                        entity.getName(),
                        Status.valueOf(entity.getStatus()),
                        entity.getEvents().stream()
                              .map(this::convert)
                              .collect(Collectors.toList())
        );
    }

    @SneakyThrows
    public DomainEvent convert(TaskEventJpaEntity taskEvent) {
        var eventClass = Match(taskEvent.getType()).of(
                Case($(TaskCreated.TYPE), TaskCreated.class),
                Case($(StatusChanged.TYPE), StatusChanged.class),
                Case($(TaskDeleted.TYPE), TaskDeleted.class)
        );
        return objectMapper.readValue(taskEvent.getBody(), eventClass);
    }

    @SneakyThrows
    public TaskEventJpaEntity convert(DomainEvent domainEvent, TaskJpaEntity taskJpaEntity) {
        return new TaskEventJpaEntity(
                domainEvent.getId().toString(),
                taskJpaEntity,
                objectMapper.writeValueAsString(domainEvent),
                domainEvent.getOccurredAt(),
                domainEvent.getType()
        );
    }
}
