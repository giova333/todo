package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.TaskFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, String>,
        JpaSpecificationExecutor<TaskJpaEntity> {

    default List<TaskJpaEntity> findAll(TaskFilter taskFilter) {
        return findAll(
                withStatus(taskFilter.getStatus())
                        .and(
                                notDeleted()
                        ));
    }

    private Specification<TaskJpaEntity> notDeleted() {
        return (root, query, cb) ->
                cb.equal(cb.coalesce(root.get("deleted"), false), false);
    }

    private Specification<TaskJpaEntity> withStatus(Status status) {
        return (root, query, cb) -> status == null
                ? cb.isTrue(cb.literal(true))
                : cb.equal(root.get("status"), status.name());
    }


}
