package com.domin0x.RESTCalling.repository;

import com.domin0x.RESTCalling.radar.StatType;

import java.math.BigDecimal;

public interface CustomizedPerGameStatsRepository {
    BigDecimal findQualifiedMaxAmountOfField(StatType statType);
    BigDecimal findQualifiedMinAmountOfField(StatType statType);

}
