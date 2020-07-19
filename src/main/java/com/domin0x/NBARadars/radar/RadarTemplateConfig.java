package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.radar.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.*;

@Configuration
public class RadarTemplateConfig {
    private static final Map<RadarType, List<StatType>> radarTypeCategoriesMap;

    private static final List<StatType> baseStatsOrdered = Arrays.asList(
            StatType.POINTS,
            StatType.OFF_REBOUNDS,
            StatType.DEF_REBOUNDS,
            StatType.ASSISTS,
            StatType.STEALS,
            StatType.BLOCKS,
            StatType.FOULS,
            StatType.TURNOVERS,
            StatType.FREE_THROWS_PCT,
            StatType.FIELD_GOALS_PCT,
            StatType.THREES_PCT
    );
    private static final List<StatType> scoringStatsOrdered = Arrays.asList(
            StatType.POINTS,
            StatType.THREES_MADE,
            StatType.THREES_ATTEMPTED,
            StatType.THREES_PCT,
            StatType.FIELD_GOALS_MADE,
            StatType.FIELD_GOALS_ATTEMPTED,
            StatType.FIELD_GOALS_PCT,
            StatType.FREE_THROWS_MADE,
            StatType.FREE_THROWS_ATTEMPTED,
            StatType.FREE_THROWS_PCT
    );

    static {
        radarTypeCategoriesMap = Collections.unmodifiableMap(Map.of(
                        RadarType.PLAYER_BASE_STATS, baseStatsOrdered
                       ,RadarType.SHOOTING_STATS, scoringStatsOrdered));

    }
    @Autowired
    private CategoryDataProvider categoryDataProvider;

    @Bean
    public Map<RadarType, RadarLayout> radarTemplatesMap(){
        Map<RadarType, RadarLayout> radarTemplatesMap = new HashMap<>();
        for (RadarType type : RadarType.values())
            radarTemplatesMap.put(type, new RadarLayout(type.name(), getCategories(type), type));

        return radarTemplatesMap;
    }

    @Bean
    public Map<RadarType, List<StatType>> radarStatTypesMap(){
        return radarTypeCategoriesMap;
    }

    private List<Category<Number>> getCategories(RadarType radarType) {
        List<Category<Number>> categories = new ArrayList<>();

        for(StatType statType : radarTypeCategoriesMap.get(radarType)){
            categories.add(createCategory(statType));
        }
        return categories;
    }

    private Category<Number> createCategory(StatType statType){
        BigDecimal inner = categoryDataProvider.minValue(statType);
        BigDecimal outer = categoryDataProvider.maxValue(statType);
        if(statType.isNegative()){
            BigDecimal tmp = inner;
            inner = outer;
            outer = tmp;
        }
        return new Category<>(categoryDataProvider.getName(statType), inner, outer);
    }

}
