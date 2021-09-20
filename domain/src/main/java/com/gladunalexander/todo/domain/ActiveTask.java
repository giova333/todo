package com.gladunalexander.todo.domain;

import lombok.Value;

@Value
public class ActiveTask implements Task {

    private static final String ACTIVE = "ACTIVE";

    TaskId id;
    String name;

    public DoneTask done() {
        return new DoneTask(id, name);
    }

    @Override
    public String getStatus() {
        return ACTIVE;
    }
}
