package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.radar.category.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class RadarTemplateConfig {
    public static Map<RadarType, List<StatType>> radarTypeCategoriesMap;

    private static List<StatType> baseStatsOrdered = Arrays.asList(
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
    private static List<StatType> scoringStatsOrdered = Arrays.asList(
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
        Map<RadarType, List<StatType>> aMap = new HashMap<>();
        aMap.put(RadarType.PLAYER_BASE_STATS, baseStatsOrdered);
        aMap.put(RadarType.SHOOTING_STATS, scoringStatsOrdered);
        radarTypeCategoriesMap = Collections.unmodifiableMap(aMap);
    }
    @Autowired
    private CategoryDataProvider categoryDataProvider;

    @Bean
    public Map<RadarType, RadarLayout> radarTemplatesMap(){
        Map<RadarType, RadarLayout> radarTemplatesMap = new HashMap<>();
        for (RadarType type  : RadarType.values())
            radarTemplatesMap.put(type, new RadarLayout(type.name(), getCategories(type), type));

        return radarTemplatesMap;
    }

    private List<Category<Number>> getCategories(RadarType radarType) {
        List<Category<Number>> categories = new ArrayList<>();

        for(StatType statType : radarTypeCategoriesMap.get(radarType)){
            categories.add(new Category<>(categoryDataProvider.getName(statType), categoryDataProvider.minValue(statType), categoryDataProvider.maxValue(statType)));
        }
        return categories;
    }
}
