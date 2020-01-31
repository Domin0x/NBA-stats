package com.domin0x.RESTCalling.radar;

import java.math.BigDecimal;

public interface RadarCategoryService {
    BigDecimal minValue();
    BigDecimal maxValue();
    BigDecimal getValue();
    String getName();
    String getPOJOPropertyName();

    void setDataSource(Object dataSource);

}
