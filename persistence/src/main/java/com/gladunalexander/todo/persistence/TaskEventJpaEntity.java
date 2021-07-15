package com.gladunalexander.todo.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task_events")
class TaskEventJpaEntity {

    @Id
    private String id;

    @ManyToOne
    private TaskJpaEntity task;

    private String body;

    private Instant occurredAt;

    private String type;
}
