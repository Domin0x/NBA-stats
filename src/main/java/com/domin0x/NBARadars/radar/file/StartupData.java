package com.domin0x.NBARadars.radar.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupData {
    private static final Logger logger = LoggerFactory.getLogger(StartupData.class);

    @Autowired
    private RadarFileService service;

    @Value("${radarImage.deleteCacheOnStart}")
    boolean deleteCacheOnStart;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        if (deleteCacheOnStart){
            service.deleteAllCachedImages();
            logger.info("Cached images were deleted");
        }
    }
}