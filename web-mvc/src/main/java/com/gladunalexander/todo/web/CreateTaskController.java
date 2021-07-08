package com.gladunalexander.todo.web;

import com.gladunalexander.todo.ports.in.CreateTaskUseCase;
import com.gladunalexander.todo.web.data.CreateTaskRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.gladunalexander.todo.ports.in.CreateTaskUseCase.CreateTaskCommand;

@Controller
@Log4j2
@RequiredArgsConstructor
class CreateTaskController {

    private final CreateTaskUseCase createTaskUseCase;

    @PostMapping("/create")
    public String createTask(@Valid CreateTaskRequest createTaskRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        log.info("Received createTaskRequest: {}", createTaskRequest);
        createTaskUseCase.create(new CreateTaskCommand(createTaskRequest.getName()));
        return "redirect:/";
    }

}
