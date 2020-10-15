package com.domin0x.NBARadars.radar.category;

import com.domin0x.NBARadars.stats.AxisDirection;
import com.domin0x.NBARadars.stats.pergame.StatType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        var min = categoryDataProvider.minValue(statType);
        var max = categoryDataProvider.maxValue(statType);
        return (statType.getAxisDirection() == AxisDirection.ASCENDING)
                ? new Category<>(categoryDataProvider.getName(statType), min, max)
                : new Category<>(categoryDataProvider.getName(statType), max, min);
    }
}
