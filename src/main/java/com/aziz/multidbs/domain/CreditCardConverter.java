package com.aziz.multidbs.domain;

import com.aziz.multidbs.config.SpringContextHelper;
import com.aziz.multidbs.service.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return getEncryptionService().encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Runtime exception while encryption");
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return getEncryptionService().decrypt(dbData);
        } catch (Exception e) {
            throw new RuntimeException("Runtime exception while decryption");
        }
    }

    private EncryptionService getEncryptionService(){
        return SpringContextHelper.getApplicationContext().getBean(EncryptionService.class);
    }
}
