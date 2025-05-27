package com.example.bankcards.service;

import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.user.UserCreateRequest;
import com.example.bankcards.dto.user.SignInRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserBlockedException;
import com.example.bankcards.exception.UserEmailDuplicateException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtUtil;
import com.example.bankcards.util.mapper.UserCreateRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserCreateRequestMapper userCreateEditMapper;

    public JwtAuthenticationResponse signUp(UserCreateRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("A user with this email already exists");
            throw new UserEmailDuplicateException();
        }
        return Optional.of(request)
                .map(userCreateEditMapper::map)
                .map(userRepository::saveAndFlush)
                .map(jwtUtil::generateToken)
                .map(JwtAuthenticationResponse::new)
                .orElse(null);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());
        if(userDetails instanceof User user && !user.isActive()){
            log.warn("Blocked user {} tries sign in", request.getEmail());
            throw new UserBlockedException();
        }
        return Optional.of(userDetails)
                .map(jwtUtil::generateToken)
                .map(JwtAuthenticationResponse::new)
                .orElse(null);
    }
}
