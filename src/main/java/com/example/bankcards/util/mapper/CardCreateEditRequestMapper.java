package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.card.CardCreateEditRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CardCreateEditRequestMapper implements Mapper<CardCreateEditRequest, Card>{

    private final UserRepository userRepository;
    private final CryptoUtil cryptoUtil;

    @Override
    public Card map(CardCreateEditRequest cardCreateEditDto) {
        Card card = new Card();
        copy(cardCreateEditDto,card);
        return card;
    }

    @Override
    public Card map(CardCreateEditRequest cardCreateEditDto, Card card) {
        copy(cardCreateEditDto,card);
        return card;
    }


    private void copy(CardCreateEditRequest cardCreateEditDto, Card card) {
        card.setBalance(cardCreateEditDto.getBalance());
        card.setNumber(cryptoUtil.encrypt(cardCreateEditDto.getNumber()));
        card.setStatus(cardCreateEditDto.getStatus());
        card.setValidityPeriod(cardCreateEditDto.getValidityPeriod());
        card.setUser(userRepository.findById(cardCreateEditDto.getUserId()).orElse(null));
    }
}
