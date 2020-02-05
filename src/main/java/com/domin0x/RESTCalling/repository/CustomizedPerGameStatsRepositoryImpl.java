package com.domin0x.RESTCalling.repository;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.radar.StatType;
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
        return (BigDecimal) em.createQuery("SELECT MAX(" + statType.getPojoPropertyName() + ") FROM PerGameStats stats WHERE stats.gamesPlayed >= 10")
                .getSingleResult();
    }

    //TODO gamesPlayed >= 10 un-hardcode this property
    public BigDecimal findQualifiedMinAmountOfField(StatType statType) {
        return (BigDecimal) em.createQuery("SELECT MIN(" + statType.getPojoPropertyName() + ") FROM PerGameStats stats WHERE stats.gamesPlayed >= 10")
                .getSingleResult();
    }

}
