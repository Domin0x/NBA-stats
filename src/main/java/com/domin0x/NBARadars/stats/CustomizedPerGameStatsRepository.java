package com.domin0x.NBARadars.stats;

import com.domin0x.NBARadars.radar.StatType;

import java.math.BigDecimal;

public interface CustomizedPerGameStatsRepository {
    BigDecimal findQualifiedMaxAmountOfField(StatType statType);
    BigDecimal findQualifiedMinAmountOfField(StatType statType);

}
