package com.gladunalexander.todo.domain.event;

import com.gladunalexander.todo.common.DomainEvent;
import com.gladunalexander.todo.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class StatusChanged implements DomainEvent {

    public static final String TYPE = "task.status-changed";

    UUID id = UUID.randomUUID();
    Instant occurredAt = Instant.now();
    UUID aggregateId;
    Status from;
    Status to;

    @Override
    public String getType() {
        return TYPE;
    }
}
