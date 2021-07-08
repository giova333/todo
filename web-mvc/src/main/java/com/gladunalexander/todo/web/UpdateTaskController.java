package com.gladunalexander.todo.web;

import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.Task;
import com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase;
import com.gladunalexander.todo.web.data.UpdateTaskStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

import static com.gladunalexander.todo.ports.in.UpdateTaskStatusUseCase.UpdateStatusCommand;

@Controller
@Log4j2
@RequiredArgsConstructor
class UpdateTaskController {

    private final UpdateTaskStatusUseCase updateTaskStatusUseCase;

    @PostMapping("/change-status")
    public String changeStatus(UpdateTaskStatusRequest request) {
        log.info("UpdateTaskStatusRequest: {}", request);
        updateTaskStatusUseCase.updateStatus(UpdateStatusCommand.builder()
                                                                .taskId(Task.TaskId.of(UUID.fromString(request.getTaskId())))
                                                                .status(Status.valueOf(request.getStatus()))
                                                                .build());
        return "redirect:/";
    }
}
