package com.domin0x.RESTCalling.service;

import com.domin0x.RESTCalling.model.RadarFile;
import com.domin0x.RESTCalling.radar.RadarLayout;
import com.domin0x.RESTCalling.radar.category.Category;
import com.domin0x.RESTCalling.repository.RadarFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RadarFileService {
    private final static String SUFFIX = ".png";

    @Autowired
    private AmazonService amazonService;

    @Autowired
    private RadarFileRepository radarFileRepository;

    @Value("${radarImage.overwriteAmazon}")
    boolean forceOverwriteAmazonCache;

    public boolean checkIfKeyExists(String key){
        return radarFileRepository.existsByPath(key) && amazonService.checkIfObjectExists(key);
    }

    public String calculateKey(RadarLayout radarLayout){
        return withoutWhitespaces(radarLayout.getTitle()) + categoriesToKeyString(radarLayout.getCategories()) + SUFFIX;
    }

    @Async("processExecutor")
    public void cacheImage(RadarLayout radarLayout, byte [] content) {
            String key = calculateKey(radarLayout);
            amazonService.uploadFile(key, content);
            if (!radarFileRepository.existsByPath(key))
                radarFileRepository.save(new RadarFile(key));
    }

    public String getImageSrcLink(String key, Map<String, Object> map){
        if (!forceOverwriteAmazonCache && checkIfKeyExists(key))
            return amazonService.getObjectURL(key);

        String baseURL = "/image/radar?";
        String params = map.entrySet().stream()
                .map(this::getEncodedRequestParam)
                .collect(Collectors.joining("&"));

        return baseURL + params;
    }

    private String getEncodedString(String string){
        try{
            return URLEncoder.encode(string, StandardCharsets.UTF_8.toString());
        }catch (UnsupportedEncodingException e){
            //TODO LOG THIS EXCEPTION
            return string;
        }
    }

    private String getEncodedRequestParam(Map.Entry <String, Object> entry) {
        return entry.getKey() + "=" + getEncodedString(entry.getValue().toString());
    }

    public void deleteCachedImage(String key){
        radarFileRepository.deleteByPath(key);
        amazonService.deleteFile(key);
    }

    public void deleteAllCachedImages(){
        amazonService.deleteAllInBucket();
        radarFileRepository.deleteAll();
    }

    public void deleteAllObjectsInBucket(){
        amazonService.deleteAllInBucket();
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
