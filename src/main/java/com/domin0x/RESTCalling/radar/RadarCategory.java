package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.service.PerGameStatsService;

import java.math.BigDecimal;

public interface RadarCategory {
    BigDecimal minValue(PerGameStatsService service);
    BigDecimal maxValue(PerGameStatsService service);
    BigDecimal getValue(PerGameStats stats);
    String getName();
    String getPOJOPropertyName();

}
