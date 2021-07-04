package com.gladunalexander.todo.api;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

import static com.gladunalexander.todo.ports.in.CreateTaskUseCase.CreateTaskCommand;

@Log4j2
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskApi {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTasksQuery getTasksQuery;

    @PostMapping
    @Timed(value = "create-task", percentiles = 0.99, histogram = true)
    @Counted(value = "create-task")
    public TaskResponse createTaskResponse(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        log.debug("createTaskRequest {}", createTaskRequest);
        var task = createTaskUseCase.create(new CreateTaskCommand(createTaskRequest.getName()));
        return TaskResponse.from(task);
    }

    @GetMapping
    @Timed(value = "get-tasks", percentiles = 0.99, histogram = true)
    @Counted(value = "get-tasks")
    public List<TaskResponse> getTasks(GetTasksFilterRequest getTasksFilterRequest) {
        var taskFilter = TaskFilter.withStatus(getTasksFilterRequest.status);
        return getTasksQuery.getTasks(taskFilter).stream()
                            .map(TaskResponse::from)
                            .collect(Collectors.toList());
    }

    @Data
    static class GetTasksFilterRequest {
        String status;
    }

    @Value
    static class TaskResponse {
        String id;
        String name;
        String status;

        public static TaskResponse from(Task task) {
            return new TaskResponse(task.getTaskId().getUuid().toString(),
                                    task.getName(),
                                    task.getStatus().name());
        }
    }

    @Value
    @Builder
    static class CreateTaskRequest {
        @NotBlank
        String name;
    }
}
