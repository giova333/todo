package com.gladunalexander.todo.web;

import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.web.data.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
class GlobalControllerAdvice {

    private final GetTasksQuery getTasksQuery;

    @ModelAttribute("tasks")
    public List<TaskResponse> tasks() {
        return getTasksQuery.getTasks().stream()
                            .map(TaskResponse::from)
                            .collect(Collectors.toList());
    }
}
