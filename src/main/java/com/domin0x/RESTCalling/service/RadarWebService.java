package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.imageUtils.ImageUtils;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RadarWebService {

    @Value("${radar.webserver.url}")
    private String baseURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RadarLayoutService radarLayoutService;

    public String getRadarImage (RadarType radarType, Player player, int season ) throws JsonProcessingException{
        RadarLayout layout = radarLayoutService.prepareRadarLayout(radarType, player, season);
        byte [] radarBinImage = getRadarImageFromAPI(radarLayoutService.radarToJsonString(layout));
        return ImageUtils.convertBinImageToString(radarBinImage);
    }

    private HttpHeaders prepareHttpHeadersForJSONRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public byte [] getRadarImageFromAPI (String jsonData) {
        HttpEntity<String> entity = new HttpEntity<>(jsonData, prepareHttpHeadersForJSONRequest());
        return restTemplate.postForObject(baseURL, entity, byte[].class );
    }

}
