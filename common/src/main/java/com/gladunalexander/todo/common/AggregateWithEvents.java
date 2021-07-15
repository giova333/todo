package com.gladunalexander.todo.common;

import lombok.Value;

import java.util.List;

@Value
public class AggregateWithEvents<T> {
    T aggregate;
    List<DomainEvent> domainEvents;

    public static <T> AggregateWithEvents<T> from(T aggregate, List<DomainEvent> domainEvents) {
        return new AggregateWithEvents<>(aggregate, domainEvents);
    }

    public static <T> AggregateWithEvents<T> from(T aggregate, DomainEvent... domainEvents) {
        return from(aggregate, List.of(domainEvents));
    }
}
