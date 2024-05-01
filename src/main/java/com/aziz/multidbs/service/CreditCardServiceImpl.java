package com.aziz.multidbs.service;

import com.aziz.multidbs.domain.cardholder.CreditCardHolder;
import com.aziz.multidbs.domain.creditcard.CreditCard;
import com.aziz.multidbs.domain.pan.CreditCardPAN;
import com.aziz.multidbs.repository.cardholder.CreditCardHolderRepository;
import com.aziz.multidbs.repository.creditcard.CreditCardRepository;
import com.aziz.multidbs.repository.pan.CreditCardPANRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardPANRepository creditCardPANRepository;

    @Override
    @Transactional //we can utilize JTA transactions across multiple XA resources by using either an Atomikos or Bitronix embedded transaction manager. For sake of simplicity, we are not using it.
    public CreditCard saveCreditCard(CreditCard creditCard) {
        //This operation is against card database.
        CreditCard saved = creditCardRepository.save(creditCard);

        //re-populate transient attributes.
        System.out.println("Before pre-populating (should be null): " + saved.getFirstName());
        saved.setCreditCardNumber(creditCard.getCreditCardNumber());
        saved.setFirstName(creditCard.getFirstName());
        saved.setLastName(creditCard.getLastName());
        saved.setZipCode(creditCard.getZipCode());

        //This operation is against cardholder database.
        creditCardHolderRepository.save(CreditCardHolder
                .builder()
                        .creditCardId(saved.getId())
                        .firstName(saved.getFirstName())
                        .lastName(saved.getLastName())
                        .zipCode(saved.getZipCode())
                .build());

        //This operation is against pan database.
        creditCardPANRepository.save(CreditCardPAN
                .builder()
                        .creditCardId(saved.getId())
                        .creditCardNumber(saved.getCreditCardNumber())
                .build());
        return saved;
    }

    @Override
    public CreditCard getCreditCardById(Long id) {
        return null;
    }
}
