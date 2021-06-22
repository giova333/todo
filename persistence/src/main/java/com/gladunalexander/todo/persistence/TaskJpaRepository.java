package com.gladunalexander.todo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, String> {
}
