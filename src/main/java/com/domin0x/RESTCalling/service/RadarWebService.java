package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RadarWebService {

    @Value("${radar.webserver.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("baseStatsTemplate")
    private RadarLayout baseTemplateLayout;

    @Autowired
    @Qualifier("scoringStatsTemplate")
    private RadarLayout scoringTemplateLayout;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PerGameStatsService perGameStatsService;

    public byte [] getRadarImage (String jsonData){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonData,headers);

        return restTemplate.postForObject(baseURL, entity, byte[].class );
    }

    public byte [] getRadarImage (RadarType radarType, Player player, int season ) throws JsonProcessingException{
        PerGameStats stats =  perGameStatsService.getPerGameStatsById(player, season);
        RadarLayout layout = createRadarLayoutFromTemplate(radarType);
        layout = fillLayoutData(layout, stats);
        String jsonRadarData = convertRadarDataToJsonString(layout);
        return getRadarImage(jsonRadarData);
    }

    public String convertRadarDataToJsonString(RadarLayout radarLayout) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(radarLayout);
    }

    private RadarLayout createRadarLayoutFromTemplate(RadarType radarType){
        switch (radarType){
            case PLAYER_BASE_STATS:
                return new RadarLayout(baseTemplateLayout);
            case SHOOTING_STATS:
                return new RadarLayout(scoringTemplateLayout);

            default: throw new IllegalArgumentException(radarType.toString() + " doesn't have any template assigned.");
        }
    }

    private RadarLayout fillLayoutData(RadarLayout layout, PerGameStats stats){
        List<Category<Number>> categories = layout.getCategories();
        int i = 0;
        for(CategoryType categoryType : RadarTemplateConfig.RadarTypeMap.get(layout.getType())){
            RadarCategoryService radarCategoryService = RadarCategoriesMappings.CATEGORY_STAT_MAP.get(categoryType);
            radarCategoryService.setDataSource(stats);
            categories.get(i++).setValue(radarCategoryService.getValue());
        }
        return layout;
    }

}
