package com.gladunalexander.todo.api;

import com.gladunalexander.todo.application.TaskNotFoundException;
import com.gladunalexander.todo.domain.IllegalStatusTransitionException;
import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.Task.TaskId;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gladunalexander.todo.ports.in.CreateTaskUseCase.CreateTaskCommand;
import static com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase.UpdateStatusCommand;

@Log4j2
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskApi {

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;
    private final GetTasksQuery getTasksQuery;

    @PostMapping
    @Timed(value = "create-task", percentiles = 0.99, histogram = true)
    @Counted(value = "create-task")
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        log.debug("createTaskRequest {}", createTaskRequest);
        var task = createTaskUseCase.create(new CreateTaskCommand(createTaskRequest.getName()));
        return TaskResponse.from(task);
    }

    @PutMapping
    @Timed(value = "update-task-status", percentiles = 0.99, histogram = true)
    @Counted(value = "update-task-status")
    public void updateTaskStatus(@Valid @RequestBody UpdateTaskStatusRequest updateTaskStatusRequest) {
        log.debug("updateTaskStatusRequest {}", updateTaskStatusRequest);
        try {
            updateTaskStatusUseCase.updateStatus(UpdateStatusCommand.builder()
                                                                    .taskId(TaskId.of(UUID.fromString(updateTaskStatusRequest.taskId)))
                                                                    .status(Status.valueOf(updateTaskStatusRequest.status))
                                                                    .build());
        } catch (IllegalStatusTransitionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    @Timed(value = "get-tasks", percentiles = 0.99, histogram = true)
    @Counted(value = "get-tasks")
    public List<TaskResponse> getTasks(GetTasksFilterRequest getTasksFilterRequest) {
        return getTasksQuery.getTasks(getTasksFilterRequest.toTaskFilter()).stream()
                            .map(TaskResponse::from)
                            .collect(Collectors.toList());
    }

    @Data
    static class GetTasksFilterRequest {
        String status;

        TaskFilter toTaskFilter() {
            return status == null
                    ? TaskFilter.empty()
                    : TaskFilter.withStatus(status);
        }
    }

    @Value
    static class TaskResponse {
        String id;
        String name;
        String status;

        static TaskResponse from(Task task) {
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

    @Value
    @Builder
    static class UpdateTaskStatusRequest {
        @NotBlank
        String taskId;
        @NotBlank
        String status;
    }
}
