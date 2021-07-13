package com.gladunalexander.todo.web.data;

import com.gladunalexander.todo.domain.ActiveTask;
import com.gladunalexander.todo.domain.DoneTask;
import com.gladunalexander.todo.domain.Task;
import lombok.Value;

import static com.gladunalexander.todo.web.data.Status.ACTIVE;
import static com.gladunalexander.todo.web.data.Status.DONE;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Value
public class TaskResponse {
    String id;
    String name;
    Status status;

    public static TaskResponse from(Task task) {
        var status = Match(task).of(
                Case($(instanceOf(ActiveTask.class)), ACTIVE),
                Case($(instanceOf(DoneTask.class)), DONE)
        );
        return new TaskResponse(task.getId().getUuid().toString(),
                                task.getName(),
                                status);
    }
}
