package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.RadarFile;
import com.domin0x.RESTCalling.radar.RadarLayout;
import com.domin0x.RESTCalling.radar.category.Category;
import com.domin0x.RESTCalling.repository.RadarFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RadarFileService {
    private final static String SUFFIX = ".png";

    @Autowired
    private AmazonService amazonService;

    @Autowired
    private RadarFileRepository radarFileRepository;


    public boolean checkIfKeyExists(String key){
        return radarFileRepository.existsByPath(key);
    }

    public String calculateKey(RadarLayout radarLayout){
        return withoutWhitespaces(radarLayout.getTitle()) + categoriesToKeyString(radarLayout.getCategories()) + SUFFIX;
    }

    @Async("processExecutor")
    public void cacheImage(RadarLayout radarLayout, byte [] content) {
            String key = calculateKey(radarLayout);
            amazonService.uploadFile(key, content);
            radarFileRepository.save(new RadarFile(key));
    }

    private String withoutWhitespaces(String str) {
        return str.replaceAll("\\s", "");
    }

    private String categoriesToKeyString(List<Category<Number>> categories) {
        return categories.stream()
                .map(this::mapRadarValueToString)
                .collect(Collectors.joining(""));
    }

    private String mapRadarValueToString(Category<Number> category){
        Number n = category.getValue();
        if (n == null)
            return "XXX";
        return n.toString().replace(".", "111");
    }
}
