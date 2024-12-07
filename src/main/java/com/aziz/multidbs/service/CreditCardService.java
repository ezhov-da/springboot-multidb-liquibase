package com.aziz.multidbs.service;

import com.aziz.multidbs.domain.creditcard.CreditCard;

import java.util.List;

public interface CreditCardService {
    CreditCard saveCreditCard(CreditCard creditCard);

    CreditCard getCreditCardById(Long id);

    List<CreditCard> getCreditCards();
}
