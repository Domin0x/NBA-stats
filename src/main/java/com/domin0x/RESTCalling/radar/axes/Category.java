package com.domin0x.RESTCalling.radar.axes;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"name", "inner", "outer"})
public class Category <T extends Number>{

    @JsonProperty("name")
    private String name;
    @JsonProperty("inner")
    private T inner;
    @JsonProperty("outer")
    private T outer;
    @JsonProperty("value")
    private T value;

    public Category(String name, T inner, T outer) {
        this.name = name;
        this.inner = inner;
        this.outer = outer;
        this.value = inner;
    }

    public Category(String name, T inner, T outer, T value) {
        this.name = name;
        this.inner = inner;
        this.outer = outer;
        this.value = value;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("inner")
    public T getInner() {
        return inner;
    }

    @JsonProperty("inner")
    public void setInner(T inner) {
        this.inner = inner;
    }

    @JsonProperty("outer")
    public T getOuter() {
        return outer;
    }

    @JsonProperty("outer")
    public void setOuter(T outer) {
        this.outer = outer;
    }

    @JsonProperty("value")
    public T getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("inner", inner).append("outer", outer).append("value", value).toString();
    }

}
