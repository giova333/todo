package com.gladunalexander.todo.web;

import com.gladunalexander.todo.domain.TaskId;
import com.gladunalexander.todo.ports.in.CompleteTaskUseCase;
import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.web.data.CreateTaskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Log4j2
@RequiredArgsConstructor
class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;

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

    @PostMapping("/complete")
    public String complete(String taskId) {
        completeTaskUseCase.complete(TaskId.fromString(taskId));
        return "redirect:/";
    }

}
