package com.gladunalexander.todo.ports.in;

import com.gladunalexander.todo.domain.DoneTask;
import com.gladunalexander.todo.domain.TaskId;

public interface CompleteTaskUseCase {

    DoneTask complete(TaskId taskId);
}
