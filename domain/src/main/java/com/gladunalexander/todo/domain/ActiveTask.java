package com.gladunalexander.todo.domain;

import lombok.Value;

@Value
public class ActiveTask implements Task {

    TaskId id;
    String name;

    public DoneTask done() {
        return new DoneTask(id, name);
    }
}
