package com.gladunalexander.todo.persistence;

import com.gladunalexander.todo.domain.Status;
import com.gladunalexander.todo.domain.TaskFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, String>,
        JpaSpecificationExecutor<TaskJpaEntity> {

    @Override
    @Query("select t from TaskJpaEntity t where t.id =:id and (t.deleted = false or t.deleted is null)")
    Optional<TaskJpaEntity> findById(@Param("id") String id);

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
