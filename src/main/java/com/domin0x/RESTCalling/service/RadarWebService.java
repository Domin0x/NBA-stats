package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.Category;
import com.domin0x.RESTCalling.radar.RadarLayout;
import com.domin0x.RESTCalling.radar.RadarType;
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

        byte [] img = restTemplate.postForObject(baseURL, entity, byte[].class );
        return img;
    }

    public byte [] getRadarImage (RadarType radarType, Player player, int season ) throws JsonProcessingException{
        PerGameStats stats =  perGameStatsService.getPerGameStatsById(player, season);
        RadarLayout layout = fillLayoutData(getTemplateLayout(radarType), stats, player);
        String jsonRadarData = convertRadarDataToJsonString(layout);
        return getRadarImage(jsonRadarData);
    }

    public String convertRadarDataToJsonString(RadarLayout radarLayout) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(radarLayout);
    }

    private RadarLayout getTemplateLayout(RadarType radarType){
        switch (radarType){
            case PLAYER_BASE_STATS:
                return baseTemplateLayout;
            case SHOOTING_STATS:
                return scoringTemplateLayout;

            default: throw new IllegalArgumentException(radarType.toString() + " doesn't have any template assigned.");
        }
    }

    private RadarLayout fillLayoutData(RadarLayout layout, PerGameStats stats, Player player ){
        List<Category> categories = layout.getCategories();
        switch(layout.getType()){
            case PLAYER_BASE_STATS:
                categories.get(0).setValue(stats.getPts());
                categories.get(1).setValue(stats.getOreb());
                categories.get(2).setValue(stats.getDreb());
                categories.get(3).setValue(stats.getAst());
                categories.get(4).setValue(stats.getStl());
                categories.get(5).setValue(stats.getBlk());
                categories.get(6).setValue(stats.getPf());
                categories.get(7).setValue(stats.getTurnovers());
                categories.get(8).setValue(stats.getFg_pct());
                categories.get(9).setValue(stats.getFg3_pct());
                categories.get(10).setValue(stats.getFt_pct());
                categories.get(11).setValue(stats.getMp());
                return layout;
            case SHOOTING_STATS:
                categories.get(0).setValue(stats.getPts());
                categories.get(1).setValue(stats.getFg3_pct());
                categories.get(2).setValue(stats.getFg3m());
                categories.get(3).setValue(stats.getFg3a());
                categories.get(4).setValue(stats.getFg_pct());
                categories.get(5).setValue(stats.getFgm());
                categories.get(6).setValue(stats.getFga());
                categories.get(7).setValue(stats.getFt_pct());
                categories.get(8).setValue(stats.getFtm());
                categories.get(9).setValue(stats.getFta());

                return layout;
            default: throw new IllegalArgumentException(layout.getType().toString() + " doesn't have any template assigned.");
        }
    }

}
