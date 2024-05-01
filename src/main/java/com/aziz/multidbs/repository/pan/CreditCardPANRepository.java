package com.aziz.multidbs.repository.pan;

import com.aziz.multidbs.domain.pan.CreditCardPAN;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardPANRepository extends JpaRepository<CreditCardPAN, Long> {
}