package com.domin0x.NBARadars.radar.category;

import com.domin0x.NBARadars.stats.StatType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class CategoriesConfig {
    private PerGameCategoryDataProvider categoryDataProvider;
    private Map<StatType,Category<Number>> categoriesByStatTypes = new HashMap<>();

    public CategoriesConfig(PerGameCategoryDataProvider categoryDataProvider) {
        this.categoryDataProvider = categoryDataProvider;
        for (var statType : StatType.values())
            categoriesByStatTypes.put(statType, createCategory(statType));
    }

    @Bean("categoriesPrototypes")
    Map<StatType,Category<Number>> categoriesByStatTypes(){
        return categoriesByStatTypes;
    }

    private Category<Number> createCategory(StatType statType){
        BigDecimal min = categoryDataProvider.minValue(statType);
        BigDecimal max = categoryDataProvider.maxValue(statType);
        return statType.isNegative() ? new Category<>(categoryDataProvider.getName(statType), max, min)
                : new Category<>(categoryDataProvider.getName(statType), min, max);
    }
}
