package com.example.bankcards.dto.card;


import com.example.bankcards.entity.Status;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate validityPeriod;

    @Pattern(regexp = "^(ACTIVE|BLOCK|EXPIRED)$", message = "Status must be ACTIVE, BLOCK, EXPIRED")
    Status status;

    @PositiveOrZero(message = "Balance must de positive or zero")
    BigDecimal balance;
}
