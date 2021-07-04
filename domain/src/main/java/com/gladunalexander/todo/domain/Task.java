package com.gladunalexander.todo.domain;

import lombok.Value;
import lombok.With;

import java.util.UUID;

@Value
@With
public class Task {

    TaskId taskId;
    String name;
    Status status;

    public static Task create(String name) {
        return new Task(TaskId.newId(), name, Status.ACTIVE);
    }

    public Task done() {
        assertIsValidTransition(status, Status.DONE);
        return withStatus(Status.DONE);
    }

    public Task cancelled() {
        assertIsValidTransition(status, Status.CANCELED);
        return withStatus(Status.CANCELED);
    }

    private void assertIsValidTransition(Status from, Status to) {
        if (!from.isValidTransition(to)) {
            throw new IllegalStatusTransitionException(from, to);
        }
    }

    @Value(staticConstructor = "of")
    public static class TaskId {
        UUID uuid;

        private static TaskId newId() {
            return new TaskId(UUID.randomUUID());
        }
    }
}