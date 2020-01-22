package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.service.PerGameStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RadarCategoriesMappings {
    @Autowired
    PerGameStatsService perGameStatsService;

    public static Map<CategoryType, RadarCategoryImpl> CATEGORY_STAT_MAP;

    static {
        System.out.println("^^^^^^^^^^^^^^^^MAPPINGS^^^^^^^^^^^^^");
        Map<CategoryType, RadarCategoryImpl> aMap = new HashMap<>();
        aMap.put(CategoryType.POINTS, new RadarCategoryImpl("Points", "pts", PerGameStats::getPts ));
        aMap.put(CategoryType.ASSISTS, new RadarCategoryImpl("Assists", "ast", PerGameStats::getAst ));
        aMap.put(CategoryType.DEF_REBOUNDS, new RadarCategoryImpl("Drebs", "dreb", PerGameStats::getDreb ));
        aMap.put(CategoryType.OFF_REBOUNDS, new RadarCategoryImpl("Orebs", "oreb", PerGameStats::getOreb ));
        aMap.put(CategoryType.REBOUNDS, new RadarCategoryImpl("Rebs", "reb", PerGameStats::getReb ));
        aMap.put(CategoryType.BLOCKS, new RadarCategoryImpl("Blocks", "blk", PerGameStats::getBlk ));
        aMap.put(CategoryType.STEALS, new RadarCategoryImpl("Steals", "stl", PerGameStats::getStl ));
        aMap.put(CategoryType.FOULS, new RadarCategoryImpl("Fouls", "pf", PerGameStats::getStl ));
        aMap.put(CategoryType.TURNOVERS, new RadarCategoryImpl("Turnovers", "tov", PerGameStats::getStl ));
        aMap.put(CategoryType.MINUTES, new RadarCategoryImpl("Minutes", "mp", PerGameStats::getMp ));

        aMap.put(CategoryType.THREES_PCT, new RadarCategoryImpl("3FG%", "fg3_pct", PerGameStats::getFg3_pct ));
        aMap.put(CategoryType.THREES_ATTEMPTED, new RadarCategoryImpl("3FGA", "fg3a", PerGameStats::getFg3a ));
        aMap.put(CategoryType.THREES_MADE, new RadarCategoryImpl("3FGM", "fg3m", PerGameStats::getFg3m ));

        aMap.put(CategoryType.FIELD_GOALS_PCT, new RadarCategoryImpl("FG%", "fg_pct", PerGameStats::getFg_pct ));
        aMap.put(CategoryType.FIELD_GOALS_ATTEMPTED, new RadarCategoryImpl("FGA", "fga", PerGameStats::getFga ));
        aMap.put(CategoryType.FIELD_GOALS_MADE, new RadarCategoryImpl("FGM", "fgm", PerGameStats::getFgm ));

        aMap.put(CategoryType.FREE_THROWS_PCT, new RadarCategoryImpl("FT%", "ft_pct", PerGameStats::getFt_pct ));
        aMap.put(CategoryType.FREE_THROWS_ATTEMPTED, new RadarCategoryImpl("FTA", "fta", PerGameStats::getFta ));
        aMap.put(CategoryType.FREE_THROWS_MADE, new RadarCategoryImpl("FTM", "ftm", PerGameStats::getFtm ));

        CATEGORY_STAT_MAP = Collections.unmodifiableMap(aMap);
    }



}
