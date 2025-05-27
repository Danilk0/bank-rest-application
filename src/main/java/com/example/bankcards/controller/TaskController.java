package com.example.bankcards.controller;

import com.example.bankcards.dto.TaskResponse;
import com.example.bankcards.entity.Task;
import com.example.bankcards.repository.TaskRepository;
import com.example.bankcards.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
