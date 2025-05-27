package com.example.bankcards.dto.card;

import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CardResponse {

    Long id;
    String number;
    LocalDate validityPeriod;
    Status status;
    BigDecimal balance;
    UserResponse user;
}
