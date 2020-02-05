package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.imageUtils.ImageUtils;
import com.domin0x.RESTCalling.model.PerGameStats;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.*;
import com.domin0x.RESTCalling.radar.category.Category;
import com.domin0x.RESTCalling.radar.StatType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RadarWebService {

    @Value("${radar.webserver.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Map<RadarType, RadarLayout> radarTemplatesMap;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PerGameStatsService perGameStatsService;

    public String getRadarImage (RadarType radarType, Player player, int season ) throws JsonProcessingException{
        RadarLayout layout = prepareRadarLayout(radarType, player, season);
        byte [] radarBinImage = getRadarImageFromAPI(RadarToJsonString(layout));
        return ImageUtils.convertBinImageToString(radarBinImage);
    }

    public String RadarToJsonString(RadarLayout radarLayout) throws JsonProcessingException{
        return new ObjectMapper().writeValueAsString(radarLayout);
    }

    public RadarLayout prepareRadarLayout(RadarType radarType, Player player, int season){
        PerGameStats stats =  perGameStatsService.getPerGameStatsById(player, season);
        RadarLayout layout = createRadarLayoutFromTemplate(radarType);
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

    private HttpHeaders prepareHttpHeadersForJSONRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public byte [] getRadarImageFromAPI (String jsonData){
        HttpHeaders headers = prepareHttpHeadersForJSONRequest();
        HttpEntity<String> entity = new HttpEntity<>(jsonData,headers);

        return restTemplate.postForObject(baseURL, entity, byte[].class );
    }

}
