package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.stats.PerGameStats;

import java.math.BigDecimal;
import java.util.function.Function;

public enum StatType {
    POINTS("pts", "Points", PerGameStats::getPts, false),
    ASSISTS("ast","Assists", PerGameStats::getAst, false),
    REBOUNDS("reb","Rebounds", PerGameStats::getReb, false),
    OFF_REBOUNDS("oreb", "Off. rebounds",PerGameStats::getOreb, false),
    DEF_REBOUNDS("dreb", "Def. rebounds",PerGameStats::getDreb, false),
    STEALS("stl", "Steals",PerGameStats::getStl, false),
    TURNOVERS("tov", "Turnovers",PerGameStats::getTurnovers, true),
    BLOCKS("blk", "Blocks",PerGameStats::getBlk, false),
    FOULS("pf", "Fouls",PerGameStats::getPf, true),
    FREE_THROWS_MADE("ftm", "FTM",PerGameStats::getFtm, false),
    FREE_THROWS_ATTEMPTED("fta", "FTA",PerGameStats::getFta, false),
    FREE_THROWS_PCT("ft_pct", "FT%",PerGameStats::getFt_pct, false),
    FIELD_GOALS_MADE("fgm", "FGM",PerGameStats::getFgm, false),
    FIELD_GOALS_ATTEMPTED("fga", "FGA",PerGameStats::getFga, false),
    FIELD_GOALS_PCT("fg_pct", "FG%",PerGameStats::getFg_pct, false),
    THREES_MADE("fg3m", "3PM", PerGameStats::getFg3m, false),
    THREES_ATTEMPTED("fg3a", "3PA", PerGameStats::getFg3a, false),
    THREES_PCT("fg3_pct", "3P%",PerGameStats::getFg3_pct, false),
    MINUTES("mp", PerGameStats::getMp, false);

    private final String pojoPropertyName;
    private final String displayName;
    private final Function<PerGameStats, BigDecimal> getStatValue;
    private final boolean isNegative;

        StatType(String pojoPropertyName, Function<PerGameStats, BigDecimal> getStatValue, boolean isNegative) {
        this.pojoPropertyName = pojoPropertyName;
        this.getStatValue = getStatValue;
        this.isNegative = isNegative;
        this.displayName = this.name();
    }

    StatType(String pojoPropertyName, String displayName, Function<PerGameStats, BigDecimal> getStatValue, boolean isNegative) {
        this.pojoPropertyName = pojoPropertyName;
        this.displayName = displayName;
        this.getStatValue = getStatValue;
        this.isNegative = isNegative;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPojoPropertyName() {
        return pojoPropertyName;
    }

    public BigDecimal getStatValue(PerGameStats stats) {
        return getStatValue.apply(stats);
    }

    public boolean isNegative() {
        return isNegative;
    }

    public static StatType fromString(String pojoPropertyName) {
        for (StatType t : StatType.values()) {
            if (t.pojoPropertyName.equalsIgnoreCase(pojoPropertyName))
                return t;
        }
        throw new  IllegalArgumentException("No stat type mapped to '" + pojoPropertyName + "'!");
    }

}
