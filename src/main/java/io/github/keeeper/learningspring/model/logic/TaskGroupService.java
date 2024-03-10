package io.github.keeeper.learningspring.model.logic;

import io.github.keeeper.learningspring.TaskConfigurationProperties;
import io.github.keeeper.learningspring.model.TaskGroup;
import io.github.keeeper.learningspring.model.TaskGroupRepository;
import io.github.keeeper.learningspring.model.TaskRepository;
import io.github.keeeper.learningspring.model.projection.GroupReadModel;
import io.github.keeeper.learningspring.model.projection.GroupWriteModel;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId))
            throw new IllegalStateException("Group has undone tasks. Finish them before continuing.");
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Task id is invalid."));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
