package com.domin0x.RESTCalling.form;

import com.domin0x.RESTCalling.radar.RadarType;

public class RadarForm {

    Integer playerId;
    RadarType radarType;
    Integer year;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public RadarType getRadarType() {
        return radarType;
    }

    public void setRadarType(RadarType radarType) {
        this.radarType = radarType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
