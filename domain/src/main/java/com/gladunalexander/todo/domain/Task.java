package com.gladunalexander.todo.domain;

public interface Task {

    TaskId getId();

    String getName();

    String getStatus();

    static ActiveTask newTask(String name) {
        return new ActiveTask(TaskId.newId(), name);
    }
}
