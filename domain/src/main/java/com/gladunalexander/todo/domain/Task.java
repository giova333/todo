package com.gladunalexander.todo.domain;

public interface Task {

    TaskId getId();

    String getName();

    static ActiveTask newTask(String name) {
        return new ActiveTask(TaskId.newId(), name);
    }
}
