package com.gladunalexander.todo.domain;

import java.util.List;

public enum Status {
    TODO, IN_PROGRESS, DONE, CANCELED;

    static {
        TODO.transitions = List.of(IN_PROGRESS, CANCELED);
        IN_PROGRESS.transitions = List.of(TODO, DONE, CANCELED);
        DONE.transitions = List.of(IN_PROGRESS);
        CANCELED.transitions = List.of();
    }

    private List<Status> transitions;

    public boolean isValidTransition(Status status) {
        return this.transitions.contains(status);
    }
}
