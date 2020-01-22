package com.domin0x.RESTCalling.repository;

import java.math.BigDecimal;

public interface CustomizedPerGameStatsRepository {
    BigDecimal findQualifiedMaxAmountOfField(String fieldName);
    BigDecimal findQualifiedMinAmountOfField(String fieldName);

}
