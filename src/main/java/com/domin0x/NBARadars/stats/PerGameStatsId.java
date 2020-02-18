package com.domin0x.NBARadars.stats;

import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.team.Team;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PerGameStatsId implements Serializable {

    public PerGameStatsId (){}

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @Column(name = "season")
    private Integer season;

    public PerGameStatsId(Player player, Team team, Integer season) {
        this.player = player;
        this.team = team;
        this.season = season;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerGameStatsId)) return false;
        PerGameStatsId that = (PerGameStatsId) o;
        return Objects.equals(player.getId(), that.player.getId()) &&
                Objects.equals(getSeason(), that.getSeason()) &&
                Objects.equals(getTeam(), that.getTeam()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getId(), getSeason(), team.getId());
    }

    @Override
    public String toString() {
        return "PerGameStatsId{" +
                "player=" + player +
                ", team=" + team +
                ", season=" + season +
                '}';
    }
}
