package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.stats.StatType;

import java.util.*;

public class RadarTemplateConfig {

    private static final List<StatType> baseStatsOrdered = Arrays.asList(
            StatType.POINTS, StatType.OFF_REBOUNDS, StatType.DEF_REBOUNDS, StatType.ASSISTS, StatType.STEALS,
            StatType.BLOCKS, StatType.FOULS, StatType.TURNOVERS, StatType.FREE_THROWS_PCT, StatType.FIELD_GOALS_PCT,
            StatType.THREES_PCT
    );
    private static final List<StatType> scoringStatsOrdered = Arrays.asList(
            StatType.POINTS, StatType.THREES_MADE, StatType.THREES_ATTEMPTED, StatType.THREES_PCT,
            StatType.FIELD_GOALS_MADE, StatType.FIELD_GOALS_ATTEMPTED, StatType.FIELD_GOALS_PCT,
            StatType.FREE_THROWS_MADE, StatType.FREE_THROWS_ATTEMPTED, StatType.FREE_THROWS_PCT
    );

    public static final Map<RadarType, List<StatType>> statTypesByRadarTemplate =
             Collections.unmodifiableMap(Map.of(RadarType.PLAYER_BASE_STATS, baseStatsOrdered
                                               ,RadarType.SHOOTING_STATS, scoringStatsOrdered));

}
