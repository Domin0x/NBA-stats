package com.domin0x.RESTCalling.repository;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.PerGameStatsId;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PerGameStatsRepository extends JpaRepository<PerGameStats, PerGameStatsId>, CustomizedPerGameStatsRepository {

        PerGameStats findByIdPlayerAndIdTeamAndIdSeason(Player player, Team team, int season);
        List<PerGameStats> findByIdPlayerAndIdSeason(Player player, int season);
        List<PerGameStats> findByIdPlayer(Player player);

        @Query(value = "SELECT MAX(pts) FROM per_game_stats", nativeQuery = true)
        BigDecimal findMaxAmountOfPoints();

        @Query(value = "SELECT MIN(season) FROM per_game_stats", nativeQuery = true)
        Integer findMinSeason();

        @Query(value = "SELECT MAX(season) FROM per_game_stats", nativeQuery = true)
        Integer findMaxSeason();

        @Query(value = "SELECT DISTINCT season FROM per_game_stats", nativeQuery = true)
        List<Integer> findAllSeasons();

}
