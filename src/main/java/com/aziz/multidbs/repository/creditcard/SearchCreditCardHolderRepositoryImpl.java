package com.aziz.multidbs.repository.creditcard;

import com.aziz.multidbs.domain.cardholder.CreditCardHolder;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchCreditCardHolderRepositoryImpl implements SearchCreditCardHolderRepository {
    // https://stackoverflow.com/questions/38549657/is-it-possible-to-add-qualifiers-in-requiredargsconstructoronconstructors
    // https://stackoverflow.com/questions/43509145/spring-boot-multiple-data-sources-using-entitymanager
    @Qualifier("cardHolderEntityManagerFactory")
    private final EntityManager entityManager;

    @Override
    public List<CreditCardHolder> findByFirstName(String firstName) {
        return entityManager
                .createQuery("select ch from CreditCardHolder ch where ch.firstName = :firstName", CreditCardHolder.class)
                .setParameter("firstName", firstName)
                .getResultList();
    }
}
