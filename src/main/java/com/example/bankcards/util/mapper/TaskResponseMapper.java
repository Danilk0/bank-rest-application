package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.TaskResponse;
import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TaskResponseMapper implements Mapper<Task, TaskResponse> {

    private final UserResponseMapper userReadMapper;

    @Override
    public TaskResponse map(Task task) {
        UserResponse userReadDto = Optional.ofNullable(task.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setUser(userReadDto);
        return taskResponse;
    }
}
