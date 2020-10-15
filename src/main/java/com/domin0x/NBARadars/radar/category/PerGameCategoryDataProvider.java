package com.domin0x.NBARadars.radar.category;

import com.domin0x.NBARadars.stats.pergame.StatType;
import com.domin0x.NBARadars.stats.pergame.PerGameStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PerGameCategoryDataProvider implements CategoryDataProvider {

    @Autowired
    PerGameStatsService service;

    @Override
    public BigDecimal minValue(StatType statType) {
        return service.getQualifiedMinOfField(statType);
    }

    @Override
    public BigDecimal maxValue(StatType statType) {
        return service.getQualifiedMaxOfField(statType);
    }

    @Override
    public String getName(StatType statType) {
        return statType.getDisplayName();
    }

}
