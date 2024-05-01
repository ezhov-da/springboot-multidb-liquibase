package com.aziz.multidbs.domain.creditcard;

import com.aziz.multidbs.domain.CreditCardConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credit_card")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardConverter.class)
    private String creditCardNumber;

    @Convert(converter = CreditCardConverter.class)
    private String cvv;

    private String expirationDate;
}
