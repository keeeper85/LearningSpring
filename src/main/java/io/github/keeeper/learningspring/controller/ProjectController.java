package io.github.keeeper.learningspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @GetMapping
    String showProjects(){
        return "projects";
    }
}
