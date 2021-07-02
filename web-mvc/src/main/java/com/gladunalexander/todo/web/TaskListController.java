package com.gladunalexander.todo.web;

import com.gladunalexander.todo.web.data.CreateTaskRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskListController {

    @GetMapping("/")
    public String index(CreateTaskRequest createTaskRequest) {
        return "task-list";
    }
}
