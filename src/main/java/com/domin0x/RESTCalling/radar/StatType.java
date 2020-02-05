package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.model.PerGameStats;

import java.math.BigDecimal;
import java.util.function.Function;

public enum StatType {
    POINTS("pts", PerGameStats::getPts),
    ASSISTS("ast", PerGameStats::getAst),
    REBOUNDS("reb", PerGameStats::getReb),
    OFF_REBOUNDS("oreb", PerGameStats::getOreb),
    DEF_REBOUNDS("dreb", PerGameStats::getDreb),
    STEALS("stl", PerGameStats::getStl),
    TURNOVERS("tov", PerGameStats::getTurnovers),
    BLOCKS("blk", PerGameStats::getBlk),
    FOULS("pf", PerGameStats::getPf),
    FREE_THROWS_MADE("ftm", PerGameStats::getFtm),
    FREE_THROWS_ATTEMPTED("fta", PerGameStats::getFta),
    FREE_THROWS_PCT("ft_pct", PerGameStats::getFt_pct),
    FIELD_GOALS_MADE("fgm", PerGameStats::getFgm),
    FIELD_GOALS_ATTEMPTED("fga", PerGameStats::getFga),
    FIELD_GOALS_PCT("fg_pct", PerGameStats::getFg_pct),
    THREES_MADE("fg3m", PerGameStats::getFg3m),
    THREES_ATTEMPTED("fg3a", PerGameStats::getFg3a),
    THREES_PCT("fg3_pct", PerGameStats::getFg3_pct),
    MINUTES("mp", PerGameStats::getMp);

    private String pojoPropertyName;
    private Function<PerGameStats, BigDecimal> getStatValue;

    public String getPojoPropertyName() {
        return pojoPropertyName;
    }

    public BigDecimal getStatValue(PerGameStats stats) {
        return getStatValue.apply(stats);
    }

    StatType(String pojoPropertyName, Function<PerGameStats, BigDecimal> getStatValue) {
        this.pojoPropertyName = pojoPropertyName;
        this.getStatValue = getStatValue;
    }

    public static StatType fromString(String pojoPropertyName) {
        for (StatType t : StatType.values()) {
            if (t.pojoPropertyName.equalsIgnoreCase(pojoPropertyName)) {
                return t;
            }
        }
        throw new  IllegalArgumentException("No stat type mapped to '" + pojoPropertyName + "'!");
    }

}
