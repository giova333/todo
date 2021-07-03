package com.gladunalexander.todo.web.data;

import com.gladunalexander.todo.domain.Task;
import lombok.Value;

@Value
public class TaskResponse {
    String id;
    String name;
    String status;

    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getTaskId().getUuid().toString(),
                                task.getName(),
                                task.getStatus().name());
    }
}
