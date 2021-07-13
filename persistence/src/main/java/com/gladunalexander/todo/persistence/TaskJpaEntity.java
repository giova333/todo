package com.gladunalexander.todo.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class TaskJpaEntity {

    @Id
    private String id;
    private String name;
    @Enumerated
    private Status status;
    private Boolean deleted;

    enum Status {
        ACTIVE, DONE
    }
}
