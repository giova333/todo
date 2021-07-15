package com.gladunalexander.todo.api.data;

import com.gladunalexander.todo.domain.TaskFilter;
import lombok.Data;

@Data
public class GetTasksFilterRequest {
    String status;

    public TaskFilter toTaskFilter() {
        return status == null
                ? TaskFilter.empty()
                : TaskFilter.withStatus(status);
    }
}