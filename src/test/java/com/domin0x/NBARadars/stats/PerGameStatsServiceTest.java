package com.domin0x.NBARadars.stats;


import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.team.Team;
import com.domin0x.NBARadars.team.TeamRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.domin0x.NBARadars.stats.PerGameStatsService.MULTIPLE_TEAMS_ABBREVIATION;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PerGameStatsService.class})
public class PerGameStatsServiceTest {

    @Autowired
    PerGameStatsService statsService;

    @MockBean
    PerGameStatsRepository statsRepository;

    @Test
    public void whenGivenExistingId_thenStatLineShouldBeReturned(){
        PerGameStats stats = new PerGameStats();
        Team team = new Team();
        Player player = new Player();
        int season = 2015;

        Mockito.when(statsRepository.findByIdPlayerAndIdTeamAndIdSeason(
                any(Player.class), any(Team.class), any(Integer.class)))
                .thenReturn(stats);

        PerGameStats obtainedStats = statsService.getPerGameStatsById(player, team, season);
        Assert.assertNotNull(obtainedStats);
    }

    @Test
    public void whenPlayerPlayedForMultipleTeamsInOneSeason_thenOneStatLineShouldBeReturned(){
        final int season = 2015;
        Player player = new Player();
        Team team1 = new Team();
        Team team2 = new Team();
        Team team3 = new Team();
        team1.setAbbreviation("T1");
        team2.setAbbreviation("T2");
        team3.setAbbreviation(MULTIPLE_TEAMS_ABBREVIATION);

        PerGameStats stats1 = PerGameStatsTestUtils.createPerGameStats(player, season, team1);
        PerGameStats stats2 = PerGameStatsTestUtils.createPerGameStats(player, season, team2);
        PerGameStats stats3 = PerGameStatsTestUtils.createPerGameStats(player, season, team3);

        List<PerGameStats> statsList = List.of(stats1, stats2, stats3);
        Mockito.when(statsRepository.findByIdPlayerAndIdSeason(any(Player.class), any(Integer.class))).thenReturn(statsList);

        PerGameStats obtainedStats = statsService.getPerGameStatsById(player, season);
        Assert.assertEquals(MULTIPLE_TEAMS_ABBREVIATION, obtainedStats.getId().getTeam().getAbbreviation());
    }

    @Test
    public void whenPlayerPlayedForMultipleSeasons_thenOrderedDescendingListShouldBeReturned() {
        final int firstSeason = 2014;
        final int middleSeason = 2015;
        final int lastSeason = 2016;

        Player player = new Player();
        Team team = new Team();
        PerGameStats stats1 = PerGameStatsTestUtils.createPerGameStats(player, firstSeason, team);
        PerGameStats stats2 = PerGameStatsTestUtils.createPerGameStats(player, middleSeason, team);
        PerGameStats stats3 = PerGameStatsTestUtils.createPerGameStats(player, lastSeason, team);

        List<PerGameStats> unorderedStatsList = List.of(stats3, stats1, stats2);
        Mockito.when(statsRepository.findByIdPlayer(any(Player.class))).thenReturn(unorderedStatsList);
        List<Integer> actual = statsService.getOrderedSeasonsForPlayer(player);
        List<Integer> sortedDescending = List.of(lastSeason, middleSeason, firstSeason);

        Assert.assertEquals(sortedDescending, actual);
    }

    @Test
    public void givenPlayer_ShouldReturnAllOfHisPerGameStats() {
        final int firstSeason = 2014;
        final int middleSeason = 2015;
        final int lastSeason = 2016;

        Player player = new Player();
        Team team = new Team();
        PerGameStats stats1 = PerGameStatsTestUtils.createPerGameStats(player, firstSeason, team);
        PerGameStats stats2 = PerGameStatsTestUtils.createPerGameStats(player, middleSeason, team);
        PerGameStats stats3 = PerGameStatsTestUtils.createPerGameStats(player, lastSeason, team);

        List<PerGameStats> expected = List.of(stats3, stats1, stats2);
        Mockito.when(statsRepository.findByIdPlayer(any(Player.class))).thenReturn(expected);
        List<PerGameStats> actual = statsService.getPerGameStatsForPlayer(player);

        Assert.assertTrue(actual.size() == expected.size()
                && actual.containsAll(expected) && expected.containsAll(actual));
    }
}
