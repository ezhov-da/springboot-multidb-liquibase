package com.aziz.multidbs.controller;

import com.aziz.multidbs.controller.model.CreditCardRequestDto;
import com.aziz.multidbs.controller.model.CreditCardResponseDto;
import com.aziz.multidbs.domain.cardholder.CreditCardHolder;
import com.aziz.multidbs.domain.creditcard.CreditCard;
import com.aziz.multidbs.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/credit-cards")
public class CreditCardRestController {
    private final CreditCardService creditCardService;

    @PostMapping
    CreditCardResponseDto saveCreditCard(@RequestBody CreditCardRequestDto creditCard) {
        CreditCard newCreditCard = creditCardService.saveCreditCard(
                CreditCard.builder()
                        .firstName(creditCard.getFirstName())
                        .lastName(creditCard.getLastName())
                        .zipCode(creditCard.getZipCode())
                        .creditCardNumber(creditCard.getCreditCardNumber())
                        .cvv(creditCard.getCvv())
                        .expirationDate(creditCard.getExpirationDate())
                        .build()
        );

        return new CreditCardResponseDto(
                newCreditCard.getId(),
                newCreditCard.getFirstName(),
                newCreditCard.getLastName(),
                newCreditCard.getZipCode(),
                newCreditCard.getCreditCardNumber(),
                newCreditCard.getCvv(),
                newCreditCard.getExpirationDate()
        );
    }

    @GetMapping("/{id}")
    CreditCardResponseDto getCreditCardById(@PathVariable Long id) {
        CreditCard creditCard = creditCardService.getCreditCardById(id);

        if (creditCard == null) {
            throw new IllegalArgumentException(id.toString());
        }

        return new CreditCardResponseDto(
                creditCard.getId(),
                creditCard.getFirstName(),
                creditCard.getLastName(),
                creditCard.getZipCode(),
                creditCard.getCreditCardNumber(),
                creditCard.getCvv(),
                creditCard.getExpirationDate()
        );
    }

    @GetMapping
    List<CreditCardResponseDto> getCreditCards() {
        List<CreditCard> creditCards = creditCardService.getCreditCards();

        return creditCards
                .stream()
                .map(creditCard ->
                        new CreditCardResponseDto(
                                creditCard.getId(),
                                creditCard.getFirstName(),
                                creditCard.getLastName(),
                                creditCard.getZipCode(),
                                creditCard.getCreditCardNumber(),
                                creditCard.getCvv(),
                                creditCard.getExpirationDate()
                        )
                ).toList();

    }

    @GetMapping("/search")
    List<CreditCardHolder> getCreditCards(@RequestParam("firstName") String firstName) {
        return creditCardService.getCreditCardHolder(firstName);
    }
}
