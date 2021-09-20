package com.gladunalexander.todo.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gladunalexander.todo.application.DeleteTaskService;
import com.gladunalexander.todo.application.TaskNotFoundException;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.domain.TaskId;
import com.gladunalexander.todo.ports.in.CompleteTaskUseCase;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.gladunalexander.todo.api.TaskApi.Status.DONE;
import static com.gladunalexander.todo.ports.in.CreateTaskUseCase.CreateTaskCommand;
import static com.gladunalexander.todo.ports.in.DeleteTaskUseCase.DeleteTaskCommand;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Log4j2
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
class TaskApi {

    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final DeleteTaskService deleteTaskService;
    private final GetTasksQuery getTasksQuery;

    @GetMapping
    @Timed(value = "get-tasks", percentiles = 0.99, histogram = true)
    @Counted(value = "get-tasks")
    public List<TaskResponse> getTasks(GetTasksFilterRequest filterRequest) {
        return getTasksQuery.getTasks(new TaskFilter(filterRequest.getStatusAsString()))
                            .stream()
                            .map(TaskResponse::from)
                            .collect(Collectors.toList());
    }

    @PostMapping
    @Timed(value = "create-task", percentiles = 0.99, histogram = true)
    @Counted(value = "create-task")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        log.debug("createTaskRequest {}", createTaskRequest);
        var task = createTaskUseCase.create(new CreateTaskCommand(createTaskRequest.getName()));
        return TaskResponse.from(task);
    }

    @PutMapping("/{id}")
    @Timed(value = "update-task-status", percentiles = 0.99, histogram = true)
    @Counted(value = "update-task-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable String id,
                             @Valid @RequestBody UpdateTaskStatusRequest updateTaskStatusRequest) {
        try {
            Match(updateTaskStatusRequest.getStatus()).of(
                    Case($(DONE), () -> completeTaskUseCase.complete(TaskId.fromString(id))),
                    Case($(), () -> {
                             throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                         }
                    ));
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Timed(value = "delete-task", percentiles = 0.99, histogram = true)
    @Counted(value = "delete-task")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        try {
            deleteTaskService.deleteTask(new DeleteTaskCommand(TaskId.fromString(id)));
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Data
    static class GetTasksFilterRequest {
        Status status;

        @JsonIgnore
        public String getStatusAsString() {
            return status != null
                    ? status.name()
                    : null;
        }
    }

    enum Status {
        ACTIVE, DONE
    }

    @Value
    static class TaskResponse {
        String id;
        String name;
        Status status;

        static TaskResponse from(Task task) {
            return new TaskResponse(task.getId().getUuid().toString(),
                                    task.getName(),
                                    Status.valueOf(task.getStatus()));
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
        @NotNull
        Status status;
    }
}
