package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.radar.axes.Category;
import com.domin0x.RESTCalling.radar.axes.CategoryType;
import com.domin0x.RESTCalling.radar.axes.CategoryMappings;
import com.domin0x.RESTCalling.radar.axes.CategoryService;
import com.domin0x.RESTCalling.service.PerGameStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class RadarTemplateConfig {
    public static Map<RadarType, List<CategoryType>> RadarTypeMap;

    private static List<CategoryType> baseStatsOrdered = Arrays.asList(
            CategoryType.POINTS,
            CategoryType.OFF_REBOUNDS,
            CategoryType.DEF_REBOUNDS,
            CategoryType.ASSISTS,
            CategoryType.STEALS,
            CategoryType.BLOCKS,
            CategoryType.FOULS,
            CategoryType.TURNOVERS,
            CategoryType.FREE_THROWS_PCT,
            CategoryType.FIELD_GOALS_PCT,
            CategoryType.THREES_PCT
    );
    private static List<CategoryType> scoringStatsOrdered = Arrays.asList(
            CategoryType.POINTS,
            CategoryType.THREES_MADE,
            CategoryType.THREES_ATTEMPTED,
            CategoryType.THREES_PCT,
            CategoryType.FIELD_GOALS_MADE,
            CategoryType.FIELD_GOALS_ATTEMPTED,
            CategoryType.FIELD_GOALS_PCT,
            CategoryType.FREE_THROWS_MADE,
            CategoryType.FREE_THROWS_ATTEMPTED,
            CategoryType.FREE_THROWS_PCT
    );

    static {
        Map<RadarType, List<CategoryType>> aMap = new HashMap<>();
        aMap.put(RadarType.PLAYER_BASE_STATS, baseStatsOrdered);
        aMap.put(RadarType.SHOOTING_STATS, scoringStatsOrdered);
        RadarTypeMap = Collections.unmodifiableMap(aMap);
    }

    @Bean
    public Map<RadarType, RadarLayout> getRadarTemplatesMap(){
        Map<RadarType, RadarLayout> radarTemplatesMap = new HashMap<>();
        for (RadarType type  : RadarTypeMap.keySet())
            radarTemplatesMap.put(type, new RadarLayout(type.name(), fillCategoriesData(type), type));

        return radarTemplatesMap;
    }

    private List<Category<Number>> fillCategoriesData(RadarType radarType) {
        List<Category<Number>> categories = new ArrayList<>();

        for(CategoryType categoryType : RadarTypeMap.get(radarType)){
            CategoryService categoryService = CategoryMappings.CATEGORY_STAT_MAP.get(categoryType);
            categories.add(new Category<>(categoryService.getName(), categoryService.minValue(), categoryService.maxValue()));
        }
        return categories;
    }
}
