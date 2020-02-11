package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.imageUtils.ImageUtils;
import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        HttpHeaders headers = prepareHttpHeadersForJSONRequest();
        HttpEntity<String> entity = new HttpEntity<>(jsonData,headers);

//        Resource resource = new ClassPathResource("images/sample-radar.png");
//        try{
//            InputStream input = resource.getInputStream();
//            return StreamUtils.copyToByteArray(input);
//        }catch (IOException e){
//            e.printStackTrace();
//            return null;
//        }
        return restTemplate.postForObject(baseURL, entity, byte[].class );
    }

}
