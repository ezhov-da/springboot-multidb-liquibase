package com.aziz.multidbs.repository.cardholder;

import com.aziz.multidbs.domain.cardholder.CreditCardHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {
}