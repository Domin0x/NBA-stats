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

import java.math.BigDecimal;
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
        List<Category<BigDecimal>> categories = layout.getCategories();
        int i = 0;
        for(CategoryType categoryType : RadarTemplateConfig.RadarTypeMap.get(layout.getType())){
            RadarCategory radarCategory = RadarCategoriesMappings.CATEGORY_STAT_MAP.get(categoryType);
            categories.get(i++).setValue(radarCategory.getValue(stats));
        }
        return layout;

//        switch(layout.getType()){
//            case PLAYER_BASE_STATS:
//                for(CategoryType type : baseStatsOrdered){
//                    RadarCategory category= RadarCategoriesMappings.CATEGORY_STAT_MAP.get(type);
//                    System.out.println("*********************************");
//                    categories.add(new Category<BigDecimal>(category.getName(), category.minValue(perGameStatsService), category.maxValue(perGameStatsService)));
//                }
//                return layout;
//            case SHOOTING_STATS:
//                categories.get(0).setValue(stats.getPts());
//                categories.get(1).setValue(stats.getFg3_pct());
//                categories.get(2).setValue(stats.getFg3m());
//                categories.get(3).setValue(stats.getFg3a());
//                categories.get(4).setValue(stats.getFg_pct());
//                categories.get(5).setValue(stats.getFgm());
//                categories.get(6).setValue(stats.getFga());
//                categories.get(7).setValue(stats.getFt_pct());
//                categories.get(8).setValue(stats.getFtm());
//                categories.get(9).setValue(stats.getFta());
//
//                return layout;
//            default: throw new IllegalArgumentException(layout.getType().toString() + " doesn't have any template assigned.");
//        }
    }

}
