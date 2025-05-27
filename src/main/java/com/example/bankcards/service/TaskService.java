package com.example.bankcards.service;

import com.example.bankcards.dto.TaskResponse;
import com.example.bankcards.entity.Task;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.TaskRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.mapper.TaskResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskResponseMapper taskResponseMapper;
    private final UserRepository userRepository;

    public List<TaskResponse> findTasks(){
        return  taskRepository.findAll().stream()
                .map(taskResponseMapper::map)
                .toList();
    }

    public boolean requestToCreateCard(){
        User currentUser = getCurrentUser();
        Task task = Task.builder()
                .user(currentUser)
                .build();
        return Optional.of(taskRepository.save(task))
                .map(Task::getId)
                .isPresent();
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
