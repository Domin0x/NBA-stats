package com.domin0x.RESTCalling.radar;

import com.domin0x.RESTCalling.service.PerGameStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RadarTemplateConfig {

    @Autowired
    PerGameStatsService perGameStatsService;

    @Bean
    @Qualifier("baseStatsTemplate")
    public RadarLayout baseStatsTemplate(){
        System.out.println("*************INSIDE baseStatsTemplate****************");

        String name = "base stats template";
        List<Category> categories = new ArrayList<>();

        categories.add(new Category<BigDecimal>("Points", BigDecimal.valueOf(0), perGameStatsService.getMaxNoOfPts()));
        categories.add(new Category<BigDecimal>("Off Rebs", BigDecimal.valueOf(0), BigDecimal.valueOf(4)));
        categories.add(new Category<BigDecimal>("Def Rebs", BigDecimal.valueOf(0), BigDecimal.valueOf(13)));
        categories.add(new Category<BigDecimal>("Ast", BigDecimal.valueOf(0), BigDecimal.valueOf(14)));
        categories.add(new Category<BigDecimal>("Steals", BigDecimal.valueOf(0), BigDecimal.valueOf(3)));
        categories.add(new Category<BigDecimal>("Blocks", BigDecimal.valueOf(0), BigDecimal.valueOf(2.8)));
        categories.add(new Category<BigDecimal>("Fouls", BigDecimal.valueOf(6), BigDecimal.valueOf(0)));
        categories.add(new Category<BigDecimal>("Turnovers", BigDecimal.valueOf(6), BigDecimal.valueOf(0)));
        categories.add(new Category<BigDecimal>("FG%", BigDecimal.valueOf(0.25), BigDecimal.valueOf(0.65)));
        categories.add(new Category<BigDecimal>("3FG%", BigDecimal.valueOf(0.20), BigDecimal.valueOf(0.52)));
        categories.add(new Category<BigDecimal>("FT%", BigDecimal.valueOf(0.40), BigDecimal.valueOf(1.0)));
        categories.add(new Category<BigDecimal>("Minutes", BigDecimal.valueOf(0), BigDecimal.valueOf(48)));

        return new RadarLayout(name, categories, RadarType.PLAYER_BASE_STATS);
    }


    @Bean
    @Qualifier("scoringStatsTemplate")
    public RadarLayout scoringStatsTemplate(){
        System.out.println("*************INSIDE scoringStatsTemplate****************");

        String name = "scoring stats template";
        List<Category> categories = new ArrayList<>();

        categories.add(new Category<BigDecimal>("Points", BigDecimal.valueOf(5), BigDecimal.valueOf(30)));
        categories.add(new Category<BigDecimal>("3FG%", BigDecimal.valueOf(0.20), BigDecimal.valueOf(0.43)));
        categories.add(new Category<BigDecimal>("3FG made", BigDecimal.valueOf(0.8), BigDecimal.valueOf(6.6)));
        categories.add(new Category<BigDecimal>("3FG att.", BigDecimal.valueOf(0.25), BigDecimal.valueOf(2.6)));
        categories.add(new Category<BigDecimal>("FG%", BigDecimal.valueOf(0.35), BigDecimal.valueOf(0.57)));
        categories.add(new Category<BigDecimal>("FG made", BigDecimal.valueOf(1.5), BigDecimal.valueOf(8.2)));
        categories.add(new Category<BigDecimal>("FG att.", BigDecimal.valueOf(3.5), BigDecimal.valueOf(17.2)));
        categories.add(new Category<BigDecimal>("FT%", BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.9)));
        categories.add(new Category<BigDecimal>("FT made", BigDecimal.valueOf(0.6), BigDecimal.valueOf(5.7)));
        categories.add(new Category<BigDecimal>("FT att.", BigDecimal.valueOf(1), BigDecimal.valueOf(7.10)));

        return new RadarLayout(name, categories, RadarType.SHOOTING_STATS);
    }
}
