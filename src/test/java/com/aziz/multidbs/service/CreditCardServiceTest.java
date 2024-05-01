package com.aziz.multidbs.service;

import com.aziz.multidbs.domain.creditcard.CreditCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CreditCardServiceTest {
    @Autowired
    CreditCardService creditCardService;

    @Test
    @Commit //to prevent rollback and view the data on the db.
    void testSaveAndGetCreditCard() {
        CreditCard cc = CreditCard.builder()
                .firstName("Aziz")
                .lastName("Moha")
                .zipCode("12345")
                //reference: https://developers.bluesnap.com/reference/test-credit-cards
                .creditCardNumber("4000000000001000")
                .cvv("901")
                .expirationDate("11/2026")
                .build();

        CreditCard savedCC = creditCardService.saveCreditCard(cc);

        assertThat(savedCC).isNotNull();
        assertThat(savedCC.getId()).isNotNull();
        assertThat(savedCC.getCreditCardNumber()).isNotNull();
        System.out.println(savedCC);
    }

}
