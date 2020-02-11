package com.domin0x.RESTCalling.controllers;

import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.RadarLayout;
import com.domin0x.RESTCalling.radar.RadarType;
import com.domin0x.RESTCalling.service.PlayerService;
import com.domin0x.RESTCalling.service.RadarFileService;
import com.domin0x.RESTCalling.service.RadarLayoutService;
import com.domin0x.RESTCalling.service.RadarWebService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private RadarWebService radarWebService;
    @Autowired
    private RadarLayoutService radarLayoutService;

    @Autowired
    private RadarFileService radarFileService;

    @ResponseBody
    @RequestMapping(value = "/radar", produces = MediaType.IMAGE_PNG_VALUE )
    public byte[] getRadarImage(@RequestParam int playerId, @RequestParam int season, @RequestParam String type) throws IOException {
        RadarType radarType = RadarType.fromString(type);
        Player player = playerService.getPlayerById(playerId);
        RadarLayout layout = radarLayoutService.prepareRadarLayout(radarType, player, season);
        byte [] radarBinImage = radarWebService.getRadarImageFromAPI(radarLayoutService.radarToJsonString(layout));

        radarFileService.cacheImage(layout, radarBinImage);

        return radarBinImage;
    }
}
