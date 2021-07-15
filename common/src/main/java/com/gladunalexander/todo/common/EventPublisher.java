package com.gladunalexander.todo.common;

import java.util.List;

public interface EventPublisher {

    void publish(List<DomainEvent> domainEvents);
}
