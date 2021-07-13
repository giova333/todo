package com.gladunalexander.todo.web;

import com.gladunalexander.todo.ports.in.GetTasksQuery;
import com.gladunalexander.todo.web.data.Status;
import com.gladunalexander.todo.web.data.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

import static com.gladunalexander.todo.web.data.Status.ACTIVE;
import static com.gladunalexander.todo.web.data.Status.DONE;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.isNull;

@ControllerAdvice
@RequiredArgsConstructor
class GlobalControllerAdvice {

    private static final String STATUS_QUERY_PARAM = "status";

    private final GetTasksQuery getTasksQuery;

    @ModelAttribute("tasks")
    public List<TaskResponse> tasks() {
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var statusParam = request.getParameter(STATUS_QUERY_PARAM);
        var status = statusParam == null ? null : Status.valueOf(statusParam);

        return Match(status).of(
                Case($(isNull()), getTasksQuery::getTasks),
                Case($(ACTIVE), getTasksQuery::getActiveTasks),
                Case($(DONE), getTasksQuery::getDoneTasks))
                                          .stream()
                                          .map(TaskResponse::from)
                                          .collect(Collectors.toList());
    }
}
