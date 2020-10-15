package com.domin0x.NBARadars.stats.pergame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class CustomizedPerGameStatsRepositoryImpl implements CustomizedPerGameStatsRepository {
    private final EntityManager em;

    @Autowired
    public CustomizedPerGameStatsRepositoryImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(PerGameStats.class);
    }

    //TODO gamesPlayed >= 10 un-hardcode this property
    public BigDecimal findQualifiedMaxAmountOfField(StatType statType) {
        String statName = statType.getPojoPropertyName();
        String query ="SELECT " + statName +
                "        FROM per_game_stats" +
                "       WHERE games_played > 30 " +
                "         AND mp > 30" +
                "       ORDER BY ABS (0.95-ROUND(PERCENT_RANK() OVER (ORDER BY " + statName + ") ,3))" +
                "       LIMIT 1";
        return (BigDecimal) em.createNativeQuery(query)
                .getSingleResult();
    }

    //TODO gamesPlayed >= 10 un-hardcode this property
    public BigDecimal findQualifiedMinAmountOfField(StatType statType) {
        return (BigDecimal) em.createQuery("SELECT MIN(" + statType.getPojoPropertyName() + ") FROM PerGameStats stats WHERE stats.gamesPlayed >= 10")
                .getSingleResult();
    }

}
