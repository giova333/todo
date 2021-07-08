package com.gladunalexander.todo.domain;

import lombok.Getter;

import java.util.List;

public enum Status {
    ACTIVE(1),
    DONE(2),
    CANCELED(3);

    static {
        ACTIVE.transitions = List.of(DONE, CANCELED);
        DONE.transitions = List.of();
        CANCELED.transitions = List.of();
    }

    private List<Status> transitions;

    @Getter
    private final int order;

    Status(int order) {
        this.order = order;
    }

    public boolean isValidTransition(Status status) {
        return this.transitions.contains(status);
    }
}
