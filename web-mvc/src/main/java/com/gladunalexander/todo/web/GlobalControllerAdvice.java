package com.gladunalexander.todo.web;

import com.gladunalexander.todo.domain.TaskFilter;
import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.web.data.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
class GlobalControllerAdvice {

    private static final String STATUS_QUERY_PARAM = "status";

    private final GetTasksQuery getTasksQuery;

    @ModelAttribute("tasks")
    public List<TaskResponse> tasks() {
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var statusParam = request.getParameter(STATUS_QUERY_PARAM);

        return getTasksQuery.getTasks(new TaskFilter(statusParam))
                            .stream()
                            .map(TaskResponse::from)
                            .collect(Collectors.toList());
    }
}
