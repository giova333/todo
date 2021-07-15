package com.gladunalexander.todo.domain.event;

import com.gladunalexander.todo.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class TaskCreated implements DomainEvent {

    public static final String TYPE = "task.created";

    UUID id = UUID.randomUUID();
    Instant occurredAt = Instant.now();
    UUID aggregateId;
    String taskName;

    @Override
    public String getType() {
        return TYPE;
    }
}
