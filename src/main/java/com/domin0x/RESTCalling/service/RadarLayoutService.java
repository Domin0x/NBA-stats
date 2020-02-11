package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.RadarLayout;
import com.domin0x.RESTCalling.radar.RadarTemplateConfig;
import com.domin0x.RESTCalling.radar.RadarType;
import com.domin0x.RESTCalling.radar.StatType;
import com.domin0x.RESTCalling.radar.category.Category;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RadarLayoutService {

    @Autowired
    private Map<RadarType, RadarLayout> radarTemplatesMap;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PerGameStatsService perGameStatsService;

    public String radarToJsonString(RadarLayout radarLayout) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(radarLayout);
    }

    public RadarLayout prepareRadarLayout(RadarType radarType, Player player, int season){
        PerGameStats stats =  perGameStatsService.getPerGameStatsById(player, season);
        RadarLayout layout = createRadarLayoutFromTemplate(radarType);
        layout.setTitle(player.getName() + " " + season + " " + radarType.getText());
        fillLayoutData(layout, stats);
        return layout;
    }

    private RadarLayout createRadarLayoutFromTemplate(RadarType templateKey){
        return new RadarLayout(radarTemplatesMap.get(templateKey));
    }

    private void fillLayoutData(RadarLayout layout, PerGameStats stats){
        List<Category<Number>> categories = layout.getCategories();
        int i = 0;
        for(StatType statType : RadarTemplateConfig.radarTypeCategoriesMap.get(layout.getType())){
            categories.get(i).setValue(statType.getStatValue(stats));
            i++;
        }
    }

}