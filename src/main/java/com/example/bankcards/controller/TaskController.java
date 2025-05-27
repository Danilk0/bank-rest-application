package com.example.bankcards.controller;

import com.example.bankcards.dto.TaskResponse;
import com.example.bankcards.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/tasks")
@RestController
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<TaskResponse> getTasks() {
        return taskService.findTasks();
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('USER')")
    public Boolean requestCard(){
        return taskService.requestToCreateCard();
    }

}
