package com.domin0x.RESTCalling.radar.axes;

import com.domin0x.RESTCalling.model.PerGameStats;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CategoryMappings {

    public static Map<CategoryType, CategoryService> CATEGORY_STAT_MAP;

    static {
        System.out.println("^^^^^^^^^^^^^^^^MAPPINGS^^^^^^^^^^^^^");
        Map<CategoryType, CategoryService> aMap = new HashMap<>();
        aMap.put(CategoryType.POINTS, new PerGameCategory("Points", "pts", PerGameStats::getPts ));
        aMap.put(CategoryType.ASSISTS, new PerGameCategory("Assists", "ast", PerGameStats::getAst ));
        aMap.put(CategoryType.DEF_REBOUNDS, new PerGameCategory("Drebs", "dreb", PerGameStats::getDreb ));
        aMap.put(CategoryType.OFF_REBOUNDS, new PerGameCategory("Orebs", "oreb", PerGameStats::getOreb ));
        aMap.put(CategoryType.REBOUNDS, new PerGameCategory("Rebs", "reb", PerGameStats::getReb ));
        aMap.put(CategoryType.BLOCKS, new PerGameCategory("Blocks", "blk", PerGameStats::getBlk ));
        aMap.put(CategoryType.STEALS, new PerGameCategory("Steals", "stl", PerGameStats::getStl ));
        aMap.put(CategoryType.FOULS, new PerGameCategory("Fouls", "pf", PerGameStats::getPf ));
        aMap.put(CategoryType.TURNOVERS, new PerGameCategory("Turnovers", "tov", PerGameStats::getTurnovers ));
        aMap.put(CategoryType.MINUTES, new PerGameCategory("Minutes", "mp", PerGameStats::getMp ));

        aMap.put(CategoryType.THREES_PCT, new PerGameCategory("3FG%", "fg3_pct", PerGameStats::getFg3_pct ));
        aMap.put(CategoryType.THREES_ATTEMPTED, new PerGameCategory("3FGA", "fg3a", PerGameStats::getFg3a ));
        aMap.put(CategoryType.THREES_MADE, new PerGameCategory("3FGM", "fg3m", PerGameStats::getFg3m ));

        aMap.put(CategoryType.FIELD_GOALS_PCT, new PerGameCategory("FG%", "fg_pct", PerGameStats::getFg_pct ));
        aMap.put(CategoryType.FIELD_GOALS_ATTEMPTED, new PerGameCategory("FGA", "fga", PerGameStats::getFga ));
        aMap.put(CategoryType.FIELD_GOALS_MADE, new PerGameCategory("FGM", "fgm", PerGameStats::getFgm ));

        aMap.put(CategoryType.FREE_THROWS_PCT, new PerGameCategory("FT%", "ft_pct", PerGameStats::getFt_pct ));
        aMap.put(CategoryType.FREE_THROWS_ATTEMPTED, new PerGameCategory("FTA", "fta", PerGameStats::getFta ));
        aMap.put(CategoryType.FREE_THROWS_MADE, new PerGameCategory("FTM", "ftm", PerGameStats::getFtm ));

        CATEGORY_STAT_MAP = Collections.unmodifiableMap(aMap);
    }

}
