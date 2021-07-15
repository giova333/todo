package com.gladunalexander.todo.api.data;

import com.gladunalexander.todo.domain.Task;
import lombok.Value;

@Value
public class BaseTaskResponse {
    String id;
    String name;
    String status;

    public static BaseTaskResponse from(Task task) {
        return new BaseTaskResponse(task.getTaskId().getUuid().toString(),
                                    task.getName(),
                                    task.getStatus().name());
    }
}