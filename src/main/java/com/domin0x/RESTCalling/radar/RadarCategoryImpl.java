package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.service.PerGameStatsService;

import java.math.BigDecimal;
import java.util.function.Function;

public class RadarCategoryImpl implements RadarCategory {

    private String name;
    private String pojoPropertyName;
    private Function<PerGameStats, BigDecimal> valueSupplier;

    public RadarCategoryImpl(String name, String pojoPropertyName, Function<PerGameStats, BigDecimal> valueSupplier) {
        this.name = name;
        this.pojoPropertyName = pojoPropertyName;
        this.valueSupplier = valueSupplier;
    }

    @Override
    public BigDecimal minValue(PerGameStatsService service) {
        return service.getQualifiedMinOfField(pojoPropertyName);
    }

    @Override
    public BigDecimal maxValue(PerGameStatsService service) {
        return service.getQualifiedMaxOfField(pojoPropertyName);
    }

    @Override
    public BigDecimal getValue(PerGameStats stats) {
        return valueSupplier.apply(stats);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPOJOPropertyName() {
        return pojoPropertyName;
    }
}
