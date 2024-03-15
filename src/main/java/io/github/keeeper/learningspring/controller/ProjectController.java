package io.github.keeeper.learningspring.controller;

import io.github.keeeper.learningspring.model.ProjectStep;
import io.github.keeeper.learningspring.model.logic.ProjectService;
import io.github.keeeper.learningspring.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    String showProjects(Model model){
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }

    @PostMapping(params = "addStep")
    String addProject(@ModelAttribute("project") ProjectWriteModel current, Model model){
        service.save(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("message", "Dodano projekt!");
        return "projects";
    }

    @PostMapping
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current){
        current.getSteps().add(new ProjectStep());
        return "projects";
    }
}
