package com.fl.web.service.impl.shop;

import com.fl.web.dao.shop.IOrderInfoDao;
import com.fl.web.entity.shop.OrderEntity;
import com.fl.web.entity.shop.TOrderInfo;
import com.fl.web.service.shop.IOrderInfoService;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：OrderInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-17 16:37
 */
@Service
@Transactional
public class OrderInfoServiceImpl implements IOrderInfoService {
    @Autowired
    private IOrderInfoDao orderInfoDao;

    @Override
    public void saveOrder(OrderEntity info) {
        List<TOrderInfo> infoList = new ArrayList<>();
        for (TOrderInfo order : info.getItemList()) {
            order.setId(UUIDUtil.get32UUID());
            order.setDeskId(info.getDeskId());
            order.setDeskCode(info.getDeskCode());
            order.setDeskName(info.getDeskName());
            order.setCustName(info.getCustName());
            order.setCustPhone(info.getCustPhone());
            order.setCustFlag(StringUtil.isNotEmpty(info.getCustPhone()) ? "Y" : "N");
            order.setStatus(StaticParam.ADD);
            order.setCreateDate(new Date());
            infoList.add(order);
        }
        orderInfoDao.saveOrderInfoBatch(infoList);
    }

    @Override
    public List<TOrderInfo> getOrderDeskList() {
        return orderInfoDao.getOrderDeskList();
    }
}
