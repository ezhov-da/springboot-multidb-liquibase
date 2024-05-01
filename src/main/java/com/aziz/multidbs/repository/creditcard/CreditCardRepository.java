package com.aziz.multidbs.repository.creditcard;

import com.aziz.multidbs.domain.creditcard.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}