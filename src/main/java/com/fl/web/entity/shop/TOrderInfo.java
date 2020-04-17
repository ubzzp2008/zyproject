package com.fl.web.entity.shop;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：TOrderInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-17 16:32
 */
@Getter
@Setter
public class TOrderInfo extends BaseEntity {
    private String deskId;
    private String deskCode;
    private String deskName;
    private String custName;
    private String custPhone;
    private String custFlag;
    private String goodsCode;
    private String goodsName;
    private String unit;
    private String num;
    private BigDecimal price;
    private BigDecimal disPrice;
    private BigDecimal totalMoney;
    private BigDecimal disMoney;
}
