package com.domin0x.NBARadars.radar;

import com.domin0x.NBARadars.radar.category.Category;
import com.domin0x.NBARadars.stats.pergame.StatType;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.domin0x.NBARadars.radar.RadarTemplateConfig.statTypesByRadarTemplate;

@Service
public class RadarPrototypeFactory {

    private Map<StatType,Category<Number>> categoriesByStatTypes;
    private Map<RadarType, RadarLayout> radarPrototypes;

    public RadarPrototypeFactory(@Qualifier("categoriesPrototypes")Map<StatType, Category<Number>> categoriesByStatTypes) {
        this.categoriesByStatTypes = categoriesByStatTypes;
    }

    @PostConstruct
    private void init(){
        radarPrototypes = initalizeTemplates();
    }

    public RadarLayout getClonedLayoutFromPrototype(RadarType type){
        return new RadarLayout(radarPrototypes.get(type));
    }

    private Map<RadarType, RadarLayout> initalizeTemplates(){
        Map<RadarType, RadarLayout> radarTemplatesMap = new HashMap<>();
        for (RadarType type : RadarType.values())
            radarTemplatesMap.put(type, new RadarLayout(type.name(), getCategories(type), type));

        return radarTemplatesMap;
    }

    private List<Category<Number>> getCategories(RadarType radarType) {
        List<Category<Number>> categories = new ArrayList<>();

        for(StatType statType : statTypesByRadarTemplate.get(radarType))
            categories.add(categoriesByStatTypes.get(statType));
        return categories;
    }
}
