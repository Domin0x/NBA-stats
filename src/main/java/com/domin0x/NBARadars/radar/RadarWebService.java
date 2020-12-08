package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.image.ImageUtils;
import com.domin0x.NBARadars.player.Player;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(RadarWebService.class);

    public String getRadarImage (RadarType radarType, Player player, int season ) throws JsonProcessingException{
        RadarLayout layout = radarLayoutService.prepareRadarLayout(radarType, player, season);
        String jsonData = radarLayoutService.radarToJsonString(layout);
        byte [] radarBinImage = getRadarImageFromAPI(jsonData);
        return ImageUtils.convertBinImageToString(radarBinImage);
    }

    private HttpHeaders prepareHttpHeadersForJSONRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public byte [] getRadarImageFromAPI (String jsonData) {
        logger.info(jsonData);
        HttpEntity<String> entity = new HttpEntity<>(jsonData, prepareHttpHeadersForJSONRequest());
        return restTemplate.postForObject(baseURL, entity, byte[].class );
    }

}
