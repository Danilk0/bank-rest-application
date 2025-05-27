package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardCreateEditRequest;
import com.example.bankcards.dto.card.CardFilter;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.dto.SendMoneyRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.WrongStatusException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.specification.CardSpecification;
import com.example.bankcards.util.mapper.CardCreateEditRequestMapper;
import com.example.bankcards.util.mapper.CardResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardCreateEditRequestMapper cardCreateEditMapper;
    private final CardResponseMapper cardReadMapper;
    private final UserRepository userRepository;
    private final CardSpecification specification;


    public CardResponse save(CardCreateEditRequest cardDto) {
        return Optional.of(cardDto)
                .map(cardCreateEditMapper::map)
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map)
                .orElse(null);
    }

    public Page<CardResponse> findAllByAuthUser(Pageable pageable, CardFilter filter) {
        return cardRepository.findAll(specification.build(filter,getCurentUser().getId()),pageable)
                .map(cardReadMapper::map);
    }

    public List<CardResponse> findAll() {
        return cardRepository.findAll().stream()
                .map(cardReadMapper::map)
                .toList();
    }

    @Transactional
    public Optional<CardResponse> update(Long id, CardCreateEditRequest newCardDto){
        return cardRepository.findById(id)
                .map(card -> cardCreateEditMapper.map(newCardDto,card))
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map);
    }

    @Transactional
    public Optional<CardResponse> updateStatus(Long id, Status newStatus){
        return cardRepository.findById(id)
                .map(card -> {
                    card.setStatus(newStatus);
                    return card;
                })
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id){
        return cardRepository.findById(id)
                        .map(card -> {
                            cardRepository.delete(card);
                            cardRepository.flush();
                            return true;
                        })
                .orElse(false);
    }

    @Transactional
    public boolean transferBetweenCards(SendMoneyRequest request) {

        if(!checkCards(request.getFrom(), request.getTo())) {
            log.error("Transfer operation is not available (check card status)");
            throw new WrongStatusException();
        }

        Card fromCard = cardRepository.findById(request.getFrom()).orElse(null);
        Card toCard = cardRepository.findById(request.getTo()).orElse(null);

        fromCard.debit(request.getAmount());
        toCard.credit(request.getAmount());

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        return true;
    }

    private boolean checkCards(Long cardId, Long cardId1) {
        User user = getCurentUser();
        return cardRepository.findAllByUserId(user.getId()).stream()
                .filter(entity -> entity.getStatus() == Status.ACTIVE)
                .map(Card::getId)
                .collect(Collectors.toSet())
                .containsAll(List.of(cardId1, cardId));

    }

    public BigDecimal countBalance() {
        User user = getCurentUser();
        return cardRepository.sumBalance(user.getId())
                .orElse(new BigDecimal(0));
    }

    @Scheduled(cron = "@monthly")
    public void checkValidPeriod(){
        log.info("Scheduled checkValidPeriod start");
        cardRepository.findAllByValidityPeriodIsLessThanEqual(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .stream()
                .filter(entity -> entity.getStatus() == Status.ACTIVE)
                .peek(card -> card.setStatus(Status.EXPIRED))
                .peek(cardRepository::saveAndFlush);
        log.info("Scheduled checkValidPeriod finish");
    }

    private User getCurentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
