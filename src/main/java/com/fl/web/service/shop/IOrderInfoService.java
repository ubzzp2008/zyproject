package com.fl.web.service.shop;

import com.fl.web.entity.shop.OrderEntity;
import com.fl.web.entity.shop.TOrderInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：IOrderInfoSerive
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-17 16:37
 */
public interface IOrderInfoService {
    void saveOrder(OrderEntity info);

    List<TOrderInfo> getOrderDeskList();

    List<TOrderInfo> getOrderByDeskId(String deskId);

    void deleteOrderInfo(String id);

    void deleteOrderByDeskId(String deskId);
}
