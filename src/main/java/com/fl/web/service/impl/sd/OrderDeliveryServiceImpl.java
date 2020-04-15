package com.fl.web.service.impl.sd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.qm.IMaraCensorDao;
import com.fl.web.dao.sd.IOrderDeliveryDao;
import com.fl.web.dao.sd.IOrderHeadDao;
import com.fl.web.dao.sd.IOrderItemDao;
import com.fl.web.entity.qm.TMaraCensor;
import com.fl.web.entity.sd.TOrderDelivery;
import com.fl.web.entity.sd.TOrderItem;
import com.fl.web.model.sd.OrderDelivery;
import com.fl.web.service.sd.IOrderDeliveryService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderDeliveryServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-08 09:40
 */
@Service
@Transactional
public class OrderDeliveryServiceImpl implements IOrderDeliveryService {
    @Autowired
    private IOrderDeliveryDao orderDeliveryDao;
    @Autowired
    private IOrderItemDao orderItemDao;
    @Autowired
    private IOrderHeadDao orderHeadDao;
    @Autowired
    private IMaraCensorDao maraCensorDao;

    @Override
    public PageInfo<TOrderDelivery> queryOrderDeliveryList(OrderDelivery info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TOrderDelivery> list = orderDeliveryDao.queryOrderDeliveryList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public Integer checkDeliveryStatus(String id, String state) {
        return orderDeliveryDao.checkDeliveryStatus(id, state);
    }

    @Override
    public Integer checkDeliveryStatusBatch(List<String> idList, String state) {
        return orderDeliveryDao.checkDeliveryStatusBatch(idList, state);
    }

    @Override
    public void deleteOrderDelivery(TOrderDelivery delivery) {
        //删除收货单(003)，更改订单行收货数量及状态，更新订单头状态
        //更新收货数据状态为删除标记（003）
        delivery.setStatus(StaticParam.DEL);
        delivery.setUpdateBy(SessionUser.getUserName());
        delivery.setUpdateDate(new Date());
        orderDeliveryDao.updateOrderDelivery(delivery);

        //回退订单行的收货数量，并更改订单行收货状态
        TOrderItem orderItem = orderItemDao.getOrderItemById(delivery.getItemId());
        if (orderItem.getDeliveryNum().subtract(delivery.getNum()).compareTo(new BigDecimal(0)) == 0) {
            orderItem.setDeliveryState("-2");//待收货
        } else {
            orderItem.setDeliveryState("-1");//部分收货
        }
        orderItem.setUsableNum(delivery.getNum());
        orderItem.setStatus(StaticParam.MOD);
        orderItem.setUpdateBy(SessionUser.getUserName());
        orderItem.setUpdateDate(new Date());
        orderItemDao.updateItemDeliveryNum(orderItem);

        //更新订单头状态
        List<TOrderItem> itemList = orderItemDao.getOrderItemByOrderNo(delivery.getOrderNo());
        String orderStatus = StaticParam.ORD_20;
        for (TOrderItem item : itemList) {
            if (!"-2".equals(item.getDeliveryState())) {//只要存在一个不是待收货的，都更新为部分收货
                //部分收货
                orderStatus = StaticParam.ORD_21;
                break;
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("orderNo", delivery.getOrderNo());
        param.put("orderStatus", orderStatus);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        param.put("status", StaticParam.MOD);
        orderHeadDao.updateOrderStatus(param);
    }

    @Override
    public void comfirmDeliveryToQM(List<TOrderDelivery> infoList) {
        List<TMaraCensor> censorList = new ArrayList<>();//送检物料集合
        for (TOrderDelivery delivery : infoList) {
            delivery.setState(StaticParam.DELIV_10);
            //构建送检数据
            TMaraCensor censor = this.initMaraCensor(delivery);
            censorList.add(censor);
            //完善修改信息
            delivery.setStatus(StaticParam.MOD);
            delivery.setUpdateBy(SessionUser.getUserName());
            delivery.setUpdateDate(new Date());
        }
        //保存送检数据
        if (CollectionUtils.isNotEmpty(censorList)) {
            maraCensorDao.saveMaraCensorBatch(censorList);
        }
        //更新数据状态
        orderDeliveryDao.updateOrderDeliveryBatch(infoList);
    }

    /**
     * @description：构建送检物料信息
     * @author：justin
     * @date：2020-01-10 10:08
     */
    private TMaraCensor initMaraCensor(TOrderDelivery item) {
        TMaraCensor mara = new TMaraCensor();
        mara.setId(UUIDUtil.get32UUID());
        mara.setOrderNo(item.getOrderNo());
        mara.setItemId(item.getId());
        mara.setItemNo(item.getItemNo());
        mara.setReqId(item.getReqId());
        mara.setMatnr(item.getMatnr());
        mara.setMaktx(item.getMaktx());
        mara.setNorms(item.getNorms());
        mara.setUnit(item.getUnit());
        mara.setMaktl(item.getMaktl());
        mara.setNum(item.getNum());
        mara.setQualityType(item.getQualityType());
        mara.setManufacturer(item.getManufacturer());
        mara.setState(StaticParam.SJ_0);//待质检收货
        mara.setDeliveryId(item.getId());
        mara.setCreateBy(SessionUser.getUserName());
        mara.setCreateDate(new Date());
        mara.setStatus(StaticParam.ADD);
        return mara;
    }

    @Override
    public void comfirmPDToStore(List<String> idList, String state) {
        List<TOrderDelivery> deliveryList = new ArrayList<>();//送检物料集合
        for (String id : idList) {
            TOrderDelivery delivery = new TOrderDelivery();
            delivery.setId(id);
            delivery.setState(state);
            delivery.setStatus(StaticParam.MOD);
            delivery.setUpdateBy(SessionUser.getUserName());
            delivery.setUpdateDate(new Date());
            deliveryList.add(delivery);
        }
        //更新数据状态
        orderDeliveryDao.updateOrderDeliveryBatch(deliveryList);
    }
}
