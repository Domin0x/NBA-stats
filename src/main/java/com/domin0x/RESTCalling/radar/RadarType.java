package com.domin0x.RESTCalling.radar;

/**
 * Created by Dominik on 27.11.2019.
 */
public enum RadarType {
    PLAYER_BASE_STATS("base stats"),
    SHOOTING_STATS("scoring");

    private String text;

    RadarType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static RadarType fromString(String text) {
        for (RadarType t : RadarType.values()) {
            if (t.text.equalsIgnoreCase(text)) {
                return t;
            }
        }
        return null;
    }

}
