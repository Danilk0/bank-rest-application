package com.example.bankcards.dto;

import com.example.bankcards.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskResponse {

    Long id;
    UserResponse user;
}
