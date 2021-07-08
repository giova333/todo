package com.gladunalexander.todo.web;

import com.gladunalexander.todo.web.data.CreateTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
class IndexController {

    @GetMapping("/")
    public String index(CreateTaskRequest createTaskRequest) {
        return "index";
    }
}
