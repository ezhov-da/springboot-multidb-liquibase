package com.aziz.multidbs.service;

import com.aziz.multidbs.domain.creditcard.CreditCard;

public interface CreditCardService {
    CreditCard saveCreditCard(CreditCard creditCard);

    CreditCard getCreditCardById(Long id);
}
