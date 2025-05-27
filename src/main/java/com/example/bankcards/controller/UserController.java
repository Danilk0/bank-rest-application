package com.example.bankcards.controller;

import com.example.bankcards.dto.SendMoneyRequest;
import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final CardService cardService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return service.findAll();
    }


    @PostMapping("/send")
    @PreAuthorize("hasAuthority('USER')")
    public Boolean sendMoney(@RequestBody @Valid SendMoneyRequest request){
        return cardService.transferBetweenCards(request);
    }

    @GetMapping("/balance")
    @PreAuthorize("hasAuthority('USER')")
    public BigDecimal getBalance(){
        return cardService.countBalance();
    }

    @GetMapping("/block/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Boolean block(@PathVariable("id") Long id){
        return service.blockUser(id);
    }
}
