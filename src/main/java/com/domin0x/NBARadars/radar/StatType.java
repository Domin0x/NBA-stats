package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.stats.PerGameStats;

import java.math.BigDecimal;
import java.util.function.Function;

public enum StatType {
    POINTS("pts", "Points", PerGameStats::getPts),
    ASSISTS("ast","Assists", PerGameStats::getAst),
    REBOUNDS("reb","Rebounds", PerGameStats::getReb),
    OFF_REBOUNDS("oreb", "Off. rebounds",PerGameStats::getOreb),
    DEF_REBOUNDS("dreb", "Def. rebounds",PerGameStats::getDreb),
    STEALS("stl", "Steals",PerGameStats::getStl),
    TURNOVERS("tov", "Turnovers",PerGameStats::getTurnovers),
    BLOCKS("blk", "Blocks",PerGameStats::getBlk),
    FOULS("pf", "Fouls",PerGameStats::getPf),
    FREE_THROWS_MADE("ftm", "FTM",PerGameStats::getFtm),
    FREE_THROWS_ATTEMPTED("fta", "FTA",PerGameStats::getFta),
    FREE_THROWS_PCT("ft_pct", "FT%",PerGameStats::getFt_pct),
    FIELD_GOALS_MADE("fgm", "FGM",PerGameStats::getFgm),
    FIELD_GOALS_ATTEMPTED("fga", "FGA",PerGameStats::getFga),
    FIELD_GOALS_PCT("fg_pct", "FG%",PerGameStats::getFg_pct),
    THREES_MADE("fg3m", "3PM", PerGameStats::getFg3m),
    THREES_ATTEMPTED("fg3a", "3PA", PerGameStats::getFg3a),
    THREES_PCT("fg3_pct", "3P%",PerGameStats::getFg3_pct),
    MINUTES("mp", PerGameStats::getMp);

    private final String pojoPropertyName;
    private final String displayName;
    private final Function<PerGameStats, BigDecimal> getStatValue;

    StatType(String pojoPropertyName, Function<PerGameStats, BigDecimal> getStatValue) {
        this.pojoPropertyName = pojoPropertyName;
        this.getStatValue = getStatValue;
        this.displayName = this.name();
    }

    StatType(String pojoPropertyName, String displayName, Function<PerGameStats, BigDecimal> getStatValue) {
        this.pojoPropertyName = pojoPropertyName;
        this.displayName = displayName;
        this.getStatValue = getStatValue;
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

    public static StatType fromString(String pojoPropertyName) {
        for (StatType t : StatType.values()) {
            if (t.pojoPropertyName.equalsIgnoreCase(pojoPropertyName))
                return t;
        }
        throw new  IllegalArgumentException("No stat type mapped to '" + pojoPropertyName + "'!");
    }

}
