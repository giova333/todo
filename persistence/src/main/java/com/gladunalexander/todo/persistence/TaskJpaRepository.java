package com.gladunalexander.todo.persistence;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import static com.gladunalexander.todo.persistence.TaskJpaEntity.Status;

interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, String>,
        JpaSpecificationExecutor<TaskJpaEntity> {

    default List<TaskJpaEntity> findAll(Status status) {
        return findAll(
                withStatus(status)
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
                : cb.equal(root.get("status"), status);
    }


}
