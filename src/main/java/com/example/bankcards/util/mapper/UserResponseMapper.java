package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper implements Mapper<User, UserResponse> {

    @Override
    public UserResponse map(User user) {
         return new UserResponse(
                 user.getId(),
                 user.getEmail(),
                 user.getBirthDate(),
                 user.getFirstname(),
                 user.getLastname(),
                 user.getRole());
    }
}
