package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.service.PerGameStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.*;

@Configuration
public class RadarTemplateConfig {
    public static Map<RadarType, List<CategoryType>> RadarTypeMap;

    public static List<CategoryType> baseStatsOrdered = Arrays.asList(
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
    public static List<CategoryType> scoringStatsOrdered = Arrays.asList(
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

    @Autowired
    PerGameStatsService perGameStatsService;

    @Bean
    @Qualifier("baseStatsTemplate")
    public RadarLayout baseStatsTemplate(){
        List<Category<BigDecimal>> categories = new ArrayList<>();
        String name = "base stats template";

        for(CategoryType type : baseStatsOrdered){
            RadarCategory category= RadarCategoriesMappings.CATEGORY_STAT_MAP.get(type);
            categories.add(new Category<>(category.getName(), category.minValue(perGameStatsService), category.maxValue(perGameStatsService)));
        }
        return new RadarLayout(name, categories, RadarType.PLAYER_BASE_STATS);
    }


    @Bean
    @Qualifier("scoringStatsTemplate")
    public RadarLayout scoringStatsTemplate(){
        String name = "scoring stats template";
        List<Category<BigDecimal>> categories = new ArrayList<>();

        for(CategoryType type : scoringStatsOrdered){
            RadarCategory category= RadarCategoriesMappings.CATEGORY_STAT_MAP.get(type);
            categories.add(new Category<>(category.getName(), category.minValue(perGameStatsService), category.maxValue(perGameStatsService)));
        }

        return new RadarLayout(name, categories, RadarType.SHOOTING_STATS);
    }
}
