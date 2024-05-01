package com.aziz.multidbs.domain.creditcard;

import com.aziz.multidbs.domain.CreditCardDataEncryptionConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardDataEncryptionConverter.class)
    private String cvv;

    @Convert(converter = CreditCardDataEncryptionConverter.class)
    private String expirationDate;

    @Transient
    private String creditCardNumber;

    @Transient
    private String firstName;

    @Transient
    private String lastName;

    @Transient
    private String zipCode;

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cvv='" + cvv + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
