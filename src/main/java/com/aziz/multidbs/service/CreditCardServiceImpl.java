package com.aziz.multidbs.service;

import com.aziz.multidbs.domain.cardholder.CreditCardHolder;
import com.aziz.multidbs.domain.creditcard.CreditCard;
import com.aziz.multidbs.domain.pan.CreditCardPAN;
import com.aziz.multidbs.repository.cardholder.CreditCardHolderRepository;
import com.aziz.multidbs.repository.creditcard.CreditCardRepository;
import com.aziz.multidbs.repository.creditcard.SearchCreditCardHolderRepository;
import com.aziz.multidbs.repository.pan.CreditCardPANRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CreditCardHolderRepository creditCardHolderRepository;
    private final CreditCardPANRepository creditCardPANRepository;
    private final SearchCreditCardHolderRepository searchCreditCardHolderRepository;

    @Override
    /*
        To coordinate transactions across multiple databases within the scope of a single @Transactional annotation,
        you would need to integrate a JTA transaction manager that can manage distributed transactions.
    */
    @Transactional
    public CreditCard saveCreditCard(CreditCard creditCard) {
        //This operation is against card database.
        CreditCard saved = creditCardRepository.save(creditCard);

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
    /*
        To coordinate transactions across multiple databases within the scope of a single @Transactional annotation,
        you would need to integrate a JTA transaction manager that can manage distributed transactions.
    */
    @Transactional
    public CreditCard getCreditCardById(Long id) {
        //This operation is against card database.
        CreditCard creditCard = creditCardRepository
                .findById(id)
                .orElseThrow(RuntimeException::new);
        //This operation is against cardholder database.
        CreditCardHolder creditCardHolder = creditCardHolderRepository
                .findByCreditCardId(creditCard.getId())
                .orElseThrow(RuntimeException::new);
        //This operation is against pan database.
        CreditCardPAN creditCardPAN = creditCardPANRepository
                .findByCreditCardId(creditCard.getId())
                .orElseThrow(RuntimeException::new);

        //set transient attributes' values
        creditCard.setFirstName(creditCardHolder.getFirstName());
        creditCard.setLastName(creditCardHolder.getLastName());
        creditCard.setZipCode(creditCardHolder.getZipCode());
        creditCard.setCreditCardNumber(creditCardPAN.getCreditCardNumber());
        return creditCard;
    }

    @Override
    public List<CreditCard> getCreditCards() {
        return creditCardRepository
                .findAll()
                .stream()
                .map(cc -> getCreditCardById(cc.getId()))
                .toList();
    }

    @Override
    public List<CreditCardHolder> getCreditCardHolder(String firstName) {
        return searchCreditCardHolderRepository.findByFirstName(firstName);
    }
}
