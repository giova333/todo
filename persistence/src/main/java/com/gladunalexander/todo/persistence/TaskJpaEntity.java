package com.gladunalexander.todo.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

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
    private String status;
    private Boolean deleted;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @OrderBy(value = "occurredAt")
    private List<TaskEventJpaEntity> events;
}
