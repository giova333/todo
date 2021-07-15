package com.gladunalexander.todo.api.data;

import com.gladunalexander.todo.common.DomainEvent;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.event.StatusChanged;
import com.gladunalexander.todo.domain.event.TaskCreated;
import com.gladunalexander.todo.domain.event.TaskDeleted;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Value
public class FullTaskResponse {
    String id;
    String name;
    String status;
    List<Event> events;

    @Value
    static class Event {
        Instant occurredAt;
        String message;

        public static Event from(DomainEvent domainEvent) {
            String message = Match(domainEvent).of(
                    Case($(instanceOf(TaskCreated.class)), "Task has been created"),
                    Case($(instanceOf(TaskDeleted.class)), "Task has been deleted"),
                    Case($(instanceOf(StatusChanged.class)),
                         () -> String.format("Status changed from %s to %s", ((StatusChanged) domainEvent).getFrom(), ((StatusChanged) domainEvent).getTo()))
            );
            return new Event(domainEvent.getOccurredAt(), message);
        }
    }

    public static FullTaskResponse from(Task task) {
        List<Event> events = task.getDomainEvents()
                                 .stream()
                                 .map(Event::from)
                                 .collect(Collectors.toList());
        return new FullTaskResponse(task.getTaskId().getUuid().toString(),
                                    task.getName(),
                                    task.getStatus().name(),
                                    events);
    }
}
