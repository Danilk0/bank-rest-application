package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.dto.user.UserResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.util.CryptoUtil;
import com.example.bankcards.util.MaskUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Component
public class CardResponseMapper implements Mapper<Card, CardResponse>{

    private final UserResponseMapper userReadMapper;
    private final CryptoUtil cryptoUtil;
    private final MaskUtil maskUtil;

    @Override
    public CardResponse map(Card card) {
        UserResponse userReadDto = Optional.ofNullable(card.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        return new CardResponse(
                card.getId(),
                maskUtil.mask(cryptoUtil.decrypt(card.getNumber())),
                card.getValidityPeriod(),
                card.getStatus(),
                card.getBalance(),
                userReadDto
        );
    }
}
