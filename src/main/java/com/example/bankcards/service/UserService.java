package com.example.bankcards.service;

import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.mapper.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.bankcards.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository repository;
    private final UserResponseMapper userReadMapper;

    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream().map(userReadMapper::map)
                .toList();
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean blockUser(Long id) {
        return repository.findById(id)
                .map(user -> {  user.setBlock(true);
                                repository.saveAndFlush(user);
                                return true;})
                .orElse(false);

    }

    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }




}
