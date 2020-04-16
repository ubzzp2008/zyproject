package com.fl.web.entity.shop;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：TGoods
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-16 10:33
 */
@Getter
@Setter
public class TGoods extends BaseEntity {
    private String goodsCode;
    private String goodsName;
    private String unit;
    private BigDecimal price;
    private BigDecimal disPrice;
    private Integer sortnum;
}
