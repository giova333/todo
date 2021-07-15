package com.gladunalexander.todo.domain;

import com.gladunalexander.todo.common.AggregateWithEvents;
import com.gladunalexander.todo.common.DomainEvent;
import com.gladunalexander.todo.domain.event.StatusChanged;
import com.gladunalexander.todo.domain.event.TaskCreated;
import com.gladunalexander.todo.domain.event.TaskDeleted;
import lombok.Value;
import lombok.With;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Value
@With
public class Task {

    TaskId taskId;
    String name;
    Status status;
    List<DomainEvent> domainEvents;

    public Task(TaskId taskId, String name, Status status, List<DomainEvent> domainEvents) {
        this.taskId = taskId;
        this.name = name;
        this.status = status;
        this.domainEvents = Collections.unmodifiableList(domainEvents);
    }

    public static AggregateWithEvents<Task> create(String name) {
        var taskId = TaskId.newId();
        var event = new TaskCreated(taskId.getUuid(), name);
        var task = new Task(taskId, name, Status.ACTIVE, List.of(event));
        return AggregateWithEvents.from(task, event);
    }

    public AggregateWithEvents<Task> updateStatus(Status newStatus) {
        assertIsValidTransition(status, newStatus);
        var event = new StatusChanged(taskId.getUuid(), status, newStatus);
        var task = withStatus(newStatus).withEvent(event);
        return AggregateWithEvents.from(task, event);
    }

    public AggregateWithEvents<Task> delete() {
        var event = new TaskDeleted(taskId.getUuid());
        var task = withEvent(event);
        return AggregateWithEvents.from(task, event);
    }

    public Task withEvent(DomainEvent domainEvent) {
        var events = new ArrayList<>(domainEvents);
        events.add(domainEvent);
        return new Task(taskId, name, status, events);
    }

    private void assertIsValidTransition(Status from, Status to) {
        if (!from.isValidTransition(to)) {
            throw new IllegalStatusTransitionException(from, to);
        }
    }

    @Value(staticConstructor = "of")
    public static class TaskId {
        UUID uuid;

        public static TaskId fromString(String id) {
            return new TaskId(UUID.fromString(id));
        }

        private static TaskId newId() {
            return new TaskId(UUID.randomUUID());
        }
    }
}