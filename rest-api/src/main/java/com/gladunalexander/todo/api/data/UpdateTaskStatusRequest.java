package com.gladunalexander.todo.api.data;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class UpdateTaskStatusRequest {
    @NotBlank
    String status;
}
