package com.aziz.multidbs.repository.creditcard;

import com.aziz.multidbs.domain.cardholder.CreditCardHolder;

import java.util.List;

public interface SearchCreditCardHolderRepository {
    List<CreditCardHolder> findByFirstName(String creditCardNumber);
}