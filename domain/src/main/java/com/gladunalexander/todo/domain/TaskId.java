package com.gladunalexander.todo.domain;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class TaskId {
    UUID uuid;

    public static TaskId fromString(String id) {
        return new TaskId(UUID.fromString(id));
    }

    public static TaskId newId() {
        return new TaskId(UUID.randomUUID());
    }
}
