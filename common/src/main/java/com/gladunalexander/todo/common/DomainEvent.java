package com.gladunalexander.todo.common;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
    UUID getId();
    UUID getAggregateId();
    String getType();
    Instant getOccurredAt();
}
