package com.gladunalexander.todo.web;

import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase;
import com.gladunalexander.todo.web.data.CreateTaskRequest;
import com.gladunalexander.todo.web.data.UpdateTaskStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@Log4j2
@RequiredArgsConstructor
class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;

    @GetMapping("/")
    public String index(CreateTaskRequest createTaskRequest) {
        return "index";
    }

    @PostMapping("/create")
    public String createTask(@Valid CreateTaskRequest createTaskRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        log.info("Received createTaskRequest: {}", createTaskRequest);
        createTaskUseCase.create(new CreateTaskUseCase.CreateTaskCommand(createTaskRequest.getName()));
        return "redirect:/";
    }

    @PostMapping("/change-status")
    public String changeStatus(UpdateTaskStatusRequest request) {
        log.info("UpdateTaskStatusRequest: {}", request);
        updateTaskStatusUseCase.updateStatus(UpdateTaskStatusUseCase.UpdateStatusCommand.builder()
                                                                                        .taskId(Task.TaskId.of(UUID.fromString(request.getTaskId())))
                                                                                        .status(Status.valueOf(request.getStatus()))
                                                                                        .build());
        return "redirect:/";
    }

}
