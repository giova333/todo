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
public class TaskDeleted implements DomainEvent {

    public static final String TYPE = "task.deleted";

    UUID id = UUID.randomUUID();
    Instant occurredAt = Instant.now();
    UUID aggregateId;

    @Override
    public String getType() {
        return TYPE;
    }
}
