package com.aziz.multidbs.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequestDto {
    private String cvv;
    private String expirationDate;
    private String creditCardNumber;
    private String firstName;
    private String lastName;
    private String zipCode;
}
