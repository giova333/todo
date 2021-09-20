package com.gladunalexander.todo.domain;

import lombok.Value;

@Value
public class DoneTask implements Task {

    private static final String DONE = "DONE";

    TaskId id;
    String name;

    @Override
    public String getStatus() {
        return DONE;
    }
}
