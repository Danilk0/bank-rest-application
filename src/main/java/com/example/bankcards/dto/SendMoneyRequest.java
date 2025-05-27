package com.example.bankcards.dto;

import com.example.bankcards.validation.ValidCard;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SendMoneyRequest {

    @ValidCard
    private Long from;

    @ValidCard
    private Long to;

    @PositiveOrZero(message = "Balance must de positive or zero")
    private BigDecimal amount;
}
