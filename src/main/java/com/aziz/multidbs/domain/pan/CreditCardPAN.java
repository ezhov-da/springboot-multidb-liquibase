package com.aziz.multidbs.domain.pan;

import com.aziz.multidbs.domain.CreditCardDataEncryptionConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credit_card_pan")
public class CreditCardPAN {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CreditCardDataEncryptionConverter.class)
    private String creditCardNumber;

    private Long creditCardId;
}
