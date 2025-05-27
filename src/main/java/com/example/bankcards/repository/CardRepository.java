package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    List<Card> findAllByUserId(Long userId);

    @Query(value = "select sum(c.balance) from Card c " +
                    "where c.user.id = ?1")
    Optional<BigDecimal> sumBalance(Long userId);

    List<Card> findAllByValidityPeriodIsLessThanEqual(LocalDate validityPeriod);
}
