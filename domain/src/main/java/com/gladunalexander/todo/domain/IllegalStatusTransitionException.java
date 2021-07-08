package com.gladunalexander.todo.domain;

import lombok.Getter;

@Getter
public class IllegalStatusTransitionException extends RuntimeException {

    private static final String MESSAGE = "Illegal status transition from: %s to: %s";

    private final Status from;
    private final Status to;

    public IllegalStatusTransitionException(Status from, Status to) {
        super(String.format(MESSAGE, from, to));
        this.from = from;
        this.to = to;
    }
}
