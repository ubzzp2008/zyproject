package com.fl.web.dao.sd;

import com.fl.web.entity.sd.TOrderItem;
import com.fl.web.model.sd.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderItemDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:04
 */
public interface IOrderItemDao {
    /**
     * @description：批量保存行项目信息
     * @author：justin
     * @date：2019-10-16 18:03
     */
    public void saveOrderItemBatch(List<TOrderItem> itemList);

    /**
     * @description：根据订单号获取行项目信息
     * @author：justin
     * @date：2019-10-16 18:26
     */
    public List<TOrderItem> getOrderItemByOrderNo(String orderNo);

    /**
     * @desc：根据id获取订单行信息
     * @author：justin
     * @date：2020/2/4 10:18
     */
    public TOrderItem getOrderItemById(String id);

    /**
     * @description：根据单号删除行项目
     * @author：justin
     * @date：2019-12-30 09:34
     */
    @Delete("update order_item set status=#{status},update_by=#{updateBy},update_date=#{updateDate} where order_no=#{orderNo} ")
    public void deleteOrderItem(Map<String, Object> param);

    /**
     * @description：更新行项目
     * @author：justin
     * @date：2019-12-30 09:47
     */
    public void updateOrderItemBatch(List<TOrderItem> itemList);

    /**
     * @description：获取待收货列表
     * @author：justin
     * @date：2019-12-30 11:47
     */
    public List<TOrderItem> queryWaitDeliveryList(OrderItem detail);

    /**
     * @description：验证收货数量是否大于可收货数量
     * @author：justin
     * @date：2020-01-08 09:35
     */
    @Select("select id,usable_num usableNum from order_item where id=#{id}")
    @ResultType(TOrderItem.class)
    public TOrderItem checkDeliveryNum(@Param("id") String id);

    /**
     * @description：更新收货信息
     * @author：justin
     * @date：2020-01-08 09:52
     */
    public void updateItemDeliveryInfo(List<TOrderItem> infoList);

    /**
     * @desc：更新收货数量及状态
     * @author：justin
     * @date：2020/2/4 10:35
     */
    public void updateItemDeliveryNum(TOrderItem orderItem);

    /**
     * @description：批量更新收货状态
     * @author：justin
     * @date：2020-01-08 15:01
     */
    public void updateItemDeliveryDiff(Map<String, Object> param);

    /**
     * @description：计算采购单收货状态值
     * @author：justin
     * @date：2020-01-08 14:13
     */
    @Select("select sum(delivery_state) from (select distinct delivery_state from order_item where order_no=#{orderNo}) t")
    @ResultType(Integer.class)
    public Integer checkDeliveryState(@Param("orderNo") String orderNo);


}
