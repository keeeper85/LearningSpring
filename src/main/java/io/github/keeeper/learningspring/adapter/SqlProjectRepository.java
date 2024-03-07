package io.github.keeeper.learningspring.adapter;

import io.github.keeeper.learningspring.model.Project;
import io.github.keeeper.learningspring.model.ProjectRepository;
import io.github.keeeper.learningspring.model.TaskGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("from Project g join fetch g.steps")
    List<Project> findAll();
}
