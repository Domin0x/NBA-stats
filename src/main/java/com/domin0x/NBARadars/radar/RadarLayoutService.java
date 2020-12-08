package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.stats.StatLine;
import com.domin0x.NBARadars.stats.pergame.PerGameStats;
import com.domin0x.NBARadars.stats.pergame.PerGameStatsService;
import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.radar.category.Category;
import com.domin0x.NBARadars.stats.pergame.StatType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.domin0x.NBARadars.radar.RadarTemplateConfig.statTypesByRadarTemplate;

@Service
public class RadarLayoutService {

    @Autowired
    RadarPrototypeFactory radarPrototypeFactory;

    @Autowired
    private PerGameStatsService perGameStatsService;

    public RadarLayout prepareRadarLayout(RadarType radarType, Player player, int season){
        PerGameStats stats =  perGameStatsService.getPerGameStatsById(player, season);
        return prepareRadarLayout(radarType, stats);
    }

    public RadarLayout prepareRadarLayout(RadarType radarType, PerGameStats stats){
        RadarLayout layout = radarPrototypeFactory.getClonedLayoutFromPrototype(radarType);
        layout.setTitle(generateTitle(radarType, stats));
        layout.setSubTitle(generateSubTitle(stats));
        fillLayoutColors(stats, layout);
        fillLayoutData(layout, stats);
        return layout;
    }

    private void fillLayoutColors(PerGameStats stats, RadarLayout layout) {
        layout.addColor("color1", stats.getId().getTeam().getColor1Hex());
        layout.addColor("color2", stats.getId().getTeam().getColor2Hex());
    }

    private String generateTitle(RadarType radarType, PerGameStats stats){
        return stats.getId().getPlayer().getName() + " " + stats.getId().getSeason() + " " + radarType.getText();
    }

    private String generateSubTitle(PerGameStats stats){
        return stats.getId().getTeam().getFullName();
    }

    private void fillLayoutData(RadarLayout layout, StatLine stats){
        List<Category<Number>> categories = layout.getCategories();
        int i = 0;
        for(StatType statType : statTypesByRadarTemplate.get(layout.getType())){
            categories.get(i).setValue(statType.getStatValue(stats));
            i++;
        }
    }

    public String radarToJsonString(RadarLayout radarLayout) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(radarLayout);
    }
}
