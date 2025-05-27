package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.user.UserCreateRequest;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserCreateRequestMapper implements Mapper<UserCreateRequest, User>{

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateRequest userCreateEditDto) {
        User user = new User();
        copy(userCreateEditDto,user);
        return user;
    }

    @Override
    public User map(UserCreateRequest userCreateEditDto, User user) {
        copy(userCreateEditDto,user);
        return user;
    }

    private void copy(UserCreateRequest userCreateEditDto, User user) {
        user.setEmail(userCreateEditDto.getEmail());
        user.setBirthDate(userCreateEditDto.getBirthDate());
        user.setFirstname(userCreateEditDto.getFirstname());
        user.setLastname(userCreateEditDto.getLastname());
        user.setPassword(passwordEncoder.encode(userCreateEditDto.getPassword()));
        user.setBlock(false);
        user.setRole(Role.USER);
    }
}
