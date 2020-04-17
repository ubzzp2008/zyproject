package com.fl.web.entity.shop;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：OrderEntity
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-17 16:47
 */
@Getter
@Setter
public class OrderEntity {
    private String deskId;
    private String deskCode;
    private String deskName;
    private String custName;
    private String custPhone;
    private List<TOrderInfo> itemList;
}
