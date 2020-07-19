package com.domin0x.NBARadars.stats;

import java.math.BigDecimal;

public interface CustomizedPerGameStatsRepository {
    BigDecimal findQualifiedMaxAmountOfField(StatType statType);
    BigDecimal findQualifiedMinAmountOfField(StatType statType);

}
