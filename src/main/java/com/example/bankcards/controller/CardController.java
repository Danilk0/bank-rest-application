package com.example.bankcards.controller;

import com.example.bankcards.dto.PageResponse;
import com.example.bankcards.dto.card.CardCreateEditRequest;
import com.example.bankcards.dto.card.CardFilter;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.entity.Status;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CardResponse> getAllCards() {
        return cardService.findAll();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public PageResponse<CardResponse> getCardsByAuthUser(Pageable pageable,@Valid CardFilter filter) {
        Page<CardResponse> page = cardService.findAllByAuthUser(pageable,filter);
        return PageResponse.of(page);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CardResponse create(@RequestBody @Valid CardCreateEditRequest request){
        return cardService.save(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CardResponse update(@PathVariable("id") Long id, @RequestBody @Valid CardCreateEditRequest request){
        return cardService.update(id, request).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CardResponse updateCardStatus(@PathVariable("id") Long id,
                                     @RequestBody @Pattern(regexp = "^(ACTIVE|BLOCK)$", message = "Status must be ACTIVE, BLOCK") Status status){
        return cardService.updateStatus(id, status).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return cardService.delete(id)
                ? noContent().build()
                : notFound().build();
    }
}
