package com.example.bankcards.service;

import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.mapper.UserResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserResponseMapper mapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("email@email.com")
                .password("password")
                .birthDate(LocalDate.now())
                .block(false)
                .role(Role.USER)
                .firstname("test")
                .lastname("test")
                .build();
    }

    @Test
    void findAll() {
        User user1 = User.builder()
                .id(2L)
                .email("email1@email.com")
                .password("password1")
                .birthDate(LocalDate.now())
                .block(false)
                .role(Role.USER)
                .firstname("test1")
                .lastname("test1")
                .build();
        doReturn(List.of(user1,user)).when(userRepository).findAll();

        List<UserResponse> userServiceAll = userService.findAll();
        assertNotNull(userServiceAll);
        assertThat(userServiceAll.size()).isEqualTo(2);
    }

    @Test
    void getByEmail() {
        doReturn(Optional.of(user)).when(userRepository).findByEmail("email@email.com");

        User actualResult = userService.getByEmail(user.getEmail());

        assertEquals(actualResult,user);
    }

}