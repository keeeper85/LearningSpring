package io.github.keeeper.learningspring.model.projection;

import io.github.keeeper.learningspring.model.Project;
import io.github.keeeper.learningspring.model.TaskGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    @NotBlank(message = "Task group description must not be empty.")
    private String description;
    @Valid
    private List<GroupTaskWriteModel> tasks = new ArrayList<>();

    public GroupWriteModel() {
        tasks.add(new GroupTaskWriteModel());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }

    public TaskGroup toGroup(Project project){
        var result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(tasks.stream()
                .map(source -> source.toTask(result))
                .collect(Collectors.toSet()));
        result.setProject(project);
        return result;
    }
}
