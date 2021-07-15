package com.gladunalexander.todo.api;

import com.gladunalexander.todo.api.data.BaseTaskResponse;
import com.gladunalexander.todo.api.data.CreateTaskRequest;
import com.gladunalexander.todo.api.data.FullTaskResponse;
import com.gladunalexander.todo.api.data.GetTasksFilterRequest;
import com.gladunalexander.todo.api.data.UpdateTaskStatusRequest;
import com.gladunalexander.todo.application.TaskNotFoundException;
import com.gladunalexander.todo.domain.IllegalStatusTransitionException;
import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.Task.TaskId;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.in.DeleteTaskUseCase;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.stream.Collectors;

import static com.gladunalexander.todo.ports.in.CreateTaskUseCase.CreateTaskCommand;
import static com.gladunalexander.todo.ports.in.DeleteTaskUseCase.DeleteTaskCommand;
import static com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase.UpdateStatusCommand;

@Log4j2
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
class TaskApi {

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;
    private final DeleteTaskUseCase deleteTaskService;
    private final GetTasksQuery getTasksQuery;

    @GetMapping
    @Timed(value = "get-tasks", percentiles = 0.99, histogram = true)
    @Counted(value = "get-tasks")
    public List<BaseTaskResponse> getTasks(GetTasksFilterRequest getTasksFilterRequest) {
        return getTasksQuery.getTasks(getTasksFilterRequest.toTaskFilter()).stream()
                            .map(BaseTaskResponse::from)
                            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Timed(value = "get-task", percentiles = 0.99, histogram = true)
    @Counted(value = "get-task")
    public FullTaskResponse getTask(@PathVariable String id) {
        try {
            var task = getTasksQuery.getTask(TaskId.fromString(id));
            return FullTaskResponse.from(task);
        } catch (TaskNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @Timed(value = "create-task", percentiles = 0.99, histogram = true)
    @Counted(value = "create-task")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseTaskResponse createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        log.debug("createTaskRequest {}", createTaskRequest);
        var task = createTaskUseCase.create(new CreateTaskCommand(createTaskRequest.getName()));
        return BaseTaskResponse.from(task);
    }

    @PutMapping("/{id}/status")
    @Timed(value = "update-task-status", percentiles = 0.99, histogram = true)
    @Counted(value = "update-task-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTaskStatus(@Valid @RequestBody UpdateTaskStatusRequest updateTaskStatusRequest,
                                 @PathVariable String id) {
        log.debug("updateTaskStatusRequest {}", updateTaskStatusRequest);
        try {
            updateTaskStatusUseCase.updateStatus(UpdateStatusCommand.builder()
                                                                    .taskId(TaskId.fromString(id))
                                                                    .status(Status.valueOf(updateTaskStatusRequest.getStatus()))
                                                                    .build());
        } catch (IllegalStatusTransitionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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

}
