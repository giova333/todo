package com.gladunalexander.todo.domain;

import java.util.List;

public enum Status {
    ACTIVE, DONE, CANCELED;

    static {
        ACTIVE.transitions = List.of(DONE, CANCELED);
        DONE.transitions = List.of();
        CANCELED.transitions = List.of();
    }

    private List<Status> transitions;

    public boolean isValidTransition(Status status) {
        return this.transitions.contains(status);
    }
}
