package com.domin0x.RESTCalling.controllers;


import com.domin0x.RESTCalling.model.Player;
import com.domin0x.RESTCalling.radar.RadarLayout;
import com.domin0x.RESTCalling.radar.RadarType;
import com.domin0x.RESTCalling.service.PerGameStatsService;
import com.domin0x.RESTCalling.service.PlayerService;
import com.domin0x.RESTCalling.service.RadarLayoutService;
import com.domin0x.RESTCalling.service.RadarWebService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PerGameStatsService perGameStatsService;
    @Autowired
    private RadarLayoutService radarLayoutService;
    @Autowired
    private RadarWebService radarWebService;
    @GetMapping({"/index", "/", ""})
    public String showMainPage(Model model) {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/png-value", produces = MediaType.IMAGE_PNG_VALUE )
    public byte[] testphoto() throws JsonProcessingException {
        Player player = playerService.getPlayerById(748);
        RadarLayout layout = radarLayoutService.prepareRadarLayout(RadarType.PLAYER_BASE_STATS, player, 2013);
        byte [] radarBinImage = radarWebService.getRadarImageFromAPI(radarLayoutService.radarToJsonString(layout));
        return radarBinImage;
    }

}
