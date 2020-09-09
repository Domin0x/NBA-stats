package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.stats.IStat;
import com.domin0x.NBARadars.stats.PerGameStats;
import com.domin0x.NBARadars.stats.PerGameStatsService;
import com.domin0x.NBARadars.player.Player;
import com.domin0x.NBARadars.radar.category.Category;
import com.domin0x.NBARadars.stats.StatType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RadarLayoutService {

    @Resource
    private Map<RadarType, RadarLayout> radarTemplatesMap;

    @Resource
    private Map<RadarType, List<StatType>> radarStatTypesMap;

    @Autowired
    private PerGameStatsService perGameStatsService;


    public RadarLayout prepareRadarLayout(RadarType radarType, Player player, int season){
        PerGameStats stats =  perGameStatsService.getPerGameStatsById(player, season);
        return prepareRadarLayout(radarType, stats);
    }

    public RadarLayout prepareRadarLayout(RadarType radarType, PerGameStats stats){
        RadarLayout layout = createRadarLayoutFromTemplate(radarType);
        layout.setTitle(generateTitle(radarType, stats));
        fillLayoutData(layout, stats);
        return layout;
    }

    private String generateTitle(RadarType radarType, PerGameStats stats){
        return stats.getId().getPlayer().getName() + " " + stats.getId().getSeason() + " " + radarType.getText();
    }

    private RadarLayout createRadarLayoutFromTemplate(RadarType templateKey){
        return new RadarLayout(radarTemplatesMap.get(templateKey));
    }

    private void fillLayoutData(RadarLayout layout, IStat stats){
        List<Category<Number>> categories = layout.getCategories();
        int i = 0;
        for(StatType statType : radarStatTypesMap.get(layout.getType())){
            categories.get(i).setValue(statType.getStatValue(stats));
            i++;
        }
    }

    public String radarToJsonString(RadarLayout radarLayout) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(radarLayout);
    }
}
