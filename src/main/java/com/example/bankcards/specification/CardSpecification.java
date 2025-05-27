package com.example.bankcards.specification;

import com.example.bankcards.dto.card.CardFilter;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CardSpecification {

    public Specification<Card> build(CardFilter filter,Long userId) {
        return withAuthUser(userId)
                .and(withValidityPeriod(filter.getValidityPeriod())
                .and(withStatus(filter.getStatus()))
                .and(withBalance(filter.getBalance())));
    }

    private Specification<Card> withAuthUser(Long userId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    private Specification<Card> withValidityPeriod(LocalDate validityPeriod) {
        return (root, query, criteriaBuilder) -> validityPeriod==null? criteriaBuilder.conjunction() :criteriaBuilder.greaterThanOrEqualTo(root.get("validityPeriod"), validityPeriod);
    }

    private Specification<Card> withStatus(Status status){
        return (root, query, criteriaBuilder) -> status==null? criteriaBuilder.conjunction() :criteriaBuilder.like(root.get("status").get("name"), status.name());
    }

    private Specification<Card> withBalance(BigDecimal balance){
        return (root, query, criteriaBuilder) -> balance==null? criteriaBuilder.conjunction() :criteriaBuilder.greaterThanOrEqualTo(root.get("balance"), balance);
    }

}
