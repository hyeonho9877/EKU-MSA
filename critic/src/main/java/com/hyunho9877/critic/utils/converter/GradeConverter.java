package com.hyunho9877.critic.utils.converter;

import com.hyunho9877.critic.domain.Grade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GradeConverter implements AttributeConverter<Grade, Double> {

    @Override
    public Double convertToDatabaseColumn(Grade attribute) {
        return attribute.getScore();
    }

    @Override
    public Grade convertToEntityAttribute(Double dbData) {
        return switch(String.valueOf(dbData)){
            case "4.5" -> Grade.AP;
            case "4.0" -> Grade.A;
            case "3.5"-> Grade.BP;
            case "3.0" -> Grade.B;
            case "2.5" -> Grade.CP;
            case "2.0" -> Grade.C;
            case "1.5" -> Grade.DP;
            case "1.0" -> Grade.D;
            case "0.0" -> Grade.F;
            default -> null;
        };
    }
}
