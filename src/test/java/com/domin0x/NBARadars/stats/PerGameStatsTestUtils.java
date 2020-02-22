package com.domin0x.NBARadars.stats;

import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.team.Team;

public class PerGameStatsTestUtils {

    public static PerGameStats createPerGameStats(Player player, int season, Team team){
        PerGameStats stats = new PerGameStats();
        stats.setId(getPerGameStatsId(player, season, team));

        return stats;
    }

    private static PerGameStatsId getPerGameStatsId(Player player, int season, Team team) {
        PerGameStatsId statsId = new PerGameStatsId();
        statsId.setPlayer(player);
        statsId.setSeason(season);
        statsId.setTeam(team);
        return statsId;
    }
}
