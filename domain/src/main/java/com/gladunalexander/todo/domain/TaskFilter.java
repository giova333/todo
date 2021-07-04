package com.gladunalexander.todo.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskFilter {

    Status status;

    public static TaskFilter withStatus(String status) {
        return new TaskFilter(Status.valueOf(status));
    }

    public static TaskFilter empty() {
        return new TaskFilter(null);
    }
}
