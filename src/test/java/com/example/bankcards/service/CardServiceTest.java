package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardCreateEditRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.util.mapper.CardCreateEditRequestMapper;
import com.example.bankcards.util.mapper.CardResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardResponseMapper cardResponseMapper;

    @Mock
    private CardCreateEditRequestMapper cardCreateEditRequestMapper;

    private Card card;
    private CardCreateEditRequest cardCreateEditRequest;
    private CardResponse cardResponse;

    @BeforeEach
    void setUp() {
        card = new Card(1L,"1234 1234 1234 1234", LocalDate.now(), Status.ACTIVE, BigDecimal.valueOf(1000L),User.builder().id(1L).build());
        cardCreateEditRequest = new CardCreateEditRequest("1234 1234 1234 1234", LocalDate.now(), Status.ACTIVE, BigDecimal.valueOf(1000L),1L);
        cardResponse = new CardResponse(1L,"1234 1234 1234 1234", LocalDate.now(), Status.ACTIVE, BigDecimal.valueOf(1000L),null);

    }
    @Test
    void save() {
        when(cardRepository.saveAndFlush(card)).thenReturn(card);
        when(cardResponseMapper.map(card)).thenReturn(cardResponse);
        when(cardCreateEditRequestMapper.map(cardCreateEditRequest)).thenReturn(card);

        CardResponse save = cardService.save(cardCreateEditRequest);

        assertEquals(save.getId(), cardResponse.getId())    ;
        assertNotNull(save);
    }


}