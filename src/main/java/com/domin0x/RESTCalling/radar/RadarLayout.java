package com.domin0x.RESTCalling.radar;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RadarLayout {

    @JsonProperty("name")
    private String title;

    @JsonIgnoreProperties
    private RadarType type;

    @JsonProperty("categories")
    private List<Category<BigDecimal>> categories;

    public RadarLayout(String name, List<Category<BigDecimal>> categories, RadarType type) {
        this.title = name;
        this.categories = categories;
        this.type = type;
    }

    @JsonProperty("name")
    public String getTitle() {
        return title;
    }


    @JsonProperty("name")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("categories")
    public List<Category<BigDecimal>> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Category<BigDecimal>> categories) {
        this.categories = categories;
    }

    public RadarType getType() {
        return type;
    }

    public void setType(RadarType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(title).append("categories", categories).toString();
    }

}
