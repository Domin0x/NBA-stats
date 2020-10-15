package com.domin0x.NBARadars.stats.pergame;

import com.domin0x.NBARadars.stats.AxisDirection;
import com.domin0x.NBARadars.stats.StatLine;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum StatType {
    POINTS("pts", "Points", PerGameStats::getPts, AxisDirection.ASCENDING),
    ASSISTS("ast","Assists", PerGameStats::getAst, AxisDirection.ASCENDING),
    REBOUNDS("reb","Rebounds", PerGameStats::getReb, AxisDirection.ASCENDING),
    OFF_REBOUNDS("oreb", "Off. rebounds",PerGameStats::getOreb, AxisDirection.ASCENDING),
    DEF_REBOUNDS("dreb", "Def. rebounds",PerGameStats::getDreb, AxisDirection.ASCENDING),
    STEALS("stl", "Steals",PerGameStats::getStl, AxisDirection.ASCENDING),
    TURNOVERS("tov", "Turnovers",PerGameStats::getTurnovers, AxisDirection.DESCENDING),
    BLOCKS("blk", "Blocks",PerGameStats::getBlk, AxisDirection.ASCENDING),
    FOULS("pf", "Fouls",PerGameStats::getPf, AxisDirection.DESCENDING),
    FREE_THROWS_MADE("ftm", "FTM",PerGameStats::getFtm, AxisDirection.ASCENDING),
    FREE_THROWS_ATTEMPTED("fta", "FTA",PerGameStats::getFta, AxisDirection.ASCENDING),
    FREE_THROWS_PCT("ft_pct", "FT%",PerGameStats::getFt_pct, AxisDirection.ASCENDING),
    FIELD_GOALS_MADE("fgm", "FGM",PerGameStats::getFgm, AxisDirection.ASCENDING),
    FIELD_GOALS_ATTEMPTED("fga", "FGA",PerGameStats::getFga, AxisDirection.ASCENDING),
    FIELD_GOALS_PCT("fg_pct", "FG%",PerGameStats::getFg_pct, AxisDirection.ASCENDING),
    THREES_MADE("fg3m", "3PM", PerGameStats::getFg3m, AxisDirection.ASCENDING),
    THREES_ATTEMPTED("fg3a", "3PA", PerGameStats::getFg3a, AxisDirection.ASCENDING),
    THREES_PCT("fg3_pct", "3P%",PerGameStats::getFg3_pct, AxisDirection.ASCENDING),
    MINUTES("mp", "Minutes", PerGameStats::getMp, AxisDirection.ASCENDING);

    private static final Map<String,StatType> ENUM_MAP =
            Stream.of(StatType.values()).collect(Collectors.toMap(StatType::getPojoPropertyName, Function.identity()));

    private final String pojoPropertyName;
    private final String displayName;
    private final Function<PerGameStats, BigDecimal> getStatValue;
    private final AxisDirection axisDirection;

    StatType(String pojoPropertyName, String displayName, Function<PerGameStats, BigDecimal> getStatValue, AxisDirection axisDirection) {
        this.pojoPropertyName = pojoPropertyName;
        this.getStatValue = getStatValue;
        this.axisDirection = axisDirection;
        this.displayName = displayName;
    }

    public static StatType fromString (String pojoPropertyName) {
        return ENUM_MAP.get(pojoPropertyName);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPojoPropertyName() {
        return pojoPropertyName;
    }

    public BigDecimal getStatValue(StatLine stats) {
        return getStatValue.apply((PerGameStats) stats);
    }

    public AxisDirection getAxisDirection() {
        return axisDirection;
    }

}
