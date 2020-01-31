package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.BeanUtils.BeanUtil;
import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.service.PerGameStatsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.function.Function;

public class PerGameCategory implements RadarCategoryService {

    private String name;
    private String pojoPropertyName;
    private Function<PerGameStats, BigDecimal> valueSupplier;
    private PerGameStats stats;

    private static PerGameStatsService service = BeanUtil.getBean(PerGameStatsService.class);

    public PerGameCategory(String name, String pojoPropertyName, Function<PerGameStats, BigDecimal> valueSupplier) {
        this.name = name;
        this.pojoPropertyName = pojoPropertyName;
        this.valueSupplier = valueSupplier;
    }

    @Override
    public void setDataSource(Object dataSource) {
        this.stats = (PerGameStats)dataSource;
    }

    @Override
    public BigDecimal minValue() {
        return service.getQualifiedMinOfField(pojoPropertyName);
    }

    @Override
    public BigDecimal maxValue() {
        return service.getQualifiedMaxOfField(pojoPropertyName);
    }

    @Override
    public BigDecimal getValue() {
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
