package com.domin0x.NBARadars.radar.category;

import com.domin0x.NBARadars.stats.StatType;

import java.math.BigDecimal;

public interface CategoryDataProvider {
    BigDecimal minValue(StatType statType);
    BigDecimal maxValue(StatType statType);
    String getName(StatType statType);
}
