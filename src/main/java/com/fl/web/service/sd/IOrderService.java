package com.fl.web.service.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.sd.TOrderHead;
import com.fl.web.entity.sd.TOrderItem;
import com.fl.web.model.sd.OrderHead;
import com.fl.web.model.sd.OrderItem;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:10
 */
public interface IOrderService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-16 17:18
     */
    public PageInfo<TOrderHead> queryOrderList(OrderHead info);

    /**
     * @description：根据单号获取单据详情
     * @author：justin
     * @date：2019-10-16 18:22
     */
    public TOrderHead getOrderByOrderNo(String orderNo);

    /**
     * @description：根据单号获取详情
     * @author：justin
     * @date：2019-12-30 10:42
     */
    public TOrderHead getOrderDetailByOrderNo(String orderNo);


    /**
     * @description：保存采购订单
     * @author：justin
     * @date：2019-10-16 19:02
     */
    public String saveOrder(TOrderHead info);

    /**
     * @description：采购需求转采购订单
     * @author：justin
     * @date：2019-12-25 09:51
     */
    public String saveReqToOrder(TOrderHead info);

    /**
     * @description：根据id检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkOrderStatus(String orderNo, String orderStatus);

    /**
     * @description：根据idList检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkOrderStatusBatch(List<String> orderNoList, String orderStatus);

    /**
     * @description：批量更新单据状态
     * @author：justin
     * @date：2019-12-25 12:19
     */
    public void updateOrderStatusBatch(List<String> orderNoList, String orderStatus, String desc, String reason);

    /**
     * @description：删除订单
     * @author：justin
     * @date：2019-12-30 09:24
     */
    public void deleteOrder(String orderNo, String reqId);

    /**
     * @description：修改采购订单
     * @author：justin
     * @date：2019-12-30 09:40
     */
    public void updateSaveOrder(TOrderHead info);

    /**
     * @description：分页查询待收货数据
     * @author：justin
     * @date：2019-12-30 12:22
     */
    PageInfo<TOrderItem> queryWaitDeliveryList(OrderItem info);

    /**
     * @description：验证收货数量大于可收货数量
     * @author：justin
     * @date：2020-01-08 09:34
     */
    public TOrderItem checkDeliveryNum(String id);

    /**
     * @description：收货操作
     * @author：justin
     * @date：2020-01-08 09:39
     */
    public void deliveryOrderItem(List<TOrderItem> infoList);

    /**
     * @description：差异收货操作
     * @author：justin
     * @date：2020-01-10 09:38
     */
    public void deliveryDiffOrderItem(List<TOrderItem> infoList);
}
