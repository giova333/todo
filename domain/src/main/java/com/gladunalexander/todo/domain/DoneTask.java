package com.gladunalexander.todo.domain;

import lombok.Value;

@Value
public class DoneTask implements Task {

    TaskId id;
    String name;
}
