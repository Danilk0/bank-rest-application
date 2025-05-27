package com.example.bankcards.dto.card;

import com.example.bankcards.entity.Status;
import com.example.bankcards.validation.ValidUser;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardCreateEditRequest {

    @Size(min = 16, max = 20,message = "The number must be between 5 and 255 characters long")
    @Pattern(regexp = "[0-9 ]{19}", message = "Card number should be in the format 1234 1234 1234 1234")
    String number;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate validityPeriod;

    @Pattern(regexp = "^(ACTIVE|BLOCK)$", message = "Status must be ACTIVE, BLOCK")
    Status status;

    @PositiveOrZero(message = "Balance must de positive or zero")
    BigDecimal balance;

    @ValidUser
    Long userId;
}
