package io.github.keeeper.learningspring.model.logic;

import io.github.keeeper.learningspring.TaskConfigurationProperties;
import io.github.keeeper.learningspring.model.*;
import io.github.keeeper.learningspring.model.projection.GroupReadModel;
import io.github.keeeper.learningspring.model.projection.GroupTaskWriteModel;
import io.github.keeeper.learningspring.model.projection.GroupWriteModel;
import io.github.keeeper.learningspring.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskGroupService taskGroupService, TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
        this.taskGroupService = taskGroupService;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }

    public Project save(ProjectWriteModel toSave){
        return repository.save(toSave.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId))
            throw new IllegalStateException("Only one undone project from group is allowed.");
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                .map(projectStep -> {
                                    var task = new GroupTaskWriteModel();
                                    task.setDescription(projectStep.getDescription());
                                    task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                    return task;
                                })
                                .collect(Collectors.toList())
                    );
                    return taskGroupService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found."));
    }
}
