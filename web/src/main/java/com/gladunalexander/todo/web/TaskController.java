package com.gladunalexander.todo.web;

import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.gladunalexander.todo.ports.in.CreateTaskUseCase.CreateTaskCommand;

@Log4j2
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;

    @PostMapping
    @Timed(value = "create-task", percentiles = 0.99, histogram = true)
    @Counted(value = "create-task")
    public CreateTaskResponse createTaskResponse(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        log.debug("Creating request {}", createTaskRequest);
        var task = createTaskUseCase.create(new CreateTaskCommand(createTaskRequest.getName()));
        return CreateTaskResponse.from(task);
    }

    @Value
    static class CreateTaskResponse {
        String id;
        String name;
        String status;

        public static CreateTaskResponse from(Task task) {
            return new CreateTaskResponse(task.getTaskId().getUuid().toString(),
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
