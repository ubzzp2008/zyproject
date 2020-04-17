package com.fl.web.entity.shop;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TOrderReport extends BaseEntity {
    private String goodsCode;
    private String goodsName;
    private String unit;
    private Integer num;
    private BigDecimal price;
    private BigDecimal money;
    private String orderDate;
}
