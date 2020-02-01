package com.domin0x.RESTCalling.radar.axes;

import java.math.BigDecimal;

public interface CategoryService {
    BigDecimal minValue();
    BigDecimal maxValue();
    BigDecimal getValue();
    String getName();
    String getPOJOPropertyName();

    void setDataSource(Object dataSource);

}
