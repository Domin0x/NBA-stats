package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.radar.RadarType;

public class RadarForm {

    Integer playerId;
    Integer year;
    RadarType radarType;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public RadarType getRadarType() {
        return radarType;
    }

    public void setRadarType(RadarType radarType) {
        this.radarType = radarType;
    }
}
