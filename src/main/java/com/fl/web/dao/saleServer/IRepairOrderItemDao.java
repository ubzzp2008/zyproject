package com.fl.web.dao.saleServer;

import com.fl.web.entity.saleServer.TRepairOrderItem;
import com.fl.web.model.saleServer.RepairOrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IDeviceRepairItemDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:32
 */
public interface IRepairOrderItemDao {
    /**
     * @description：根据单号获取单据行项目信息
     * @author：justin
     * @date：2019-10-25 15:50
     */
    public List<TRepairOrderItem> getOrderItemByOrderNo(String orderNo);

    /**
     * @description：批量保存单据行信息
     * @author：justin
     * @date：2019-10-25 16:16
     */
    public void saveRepairOrderItem(List<TRepairOrderItem> itemList);

    /**
     * @description：根据id更新状态
     * @author：justin
     * @date：2019-11-01 13:54
     */
    @Update("update repair_order_item set deal_state=#{dealState},status=#{status},update_by=#{updateBy},update_date=#{updateDate} where id=#{id}")
    public void updateRepairOrderItemById(Map<String, Object> param);

    /**
     * @description：检查单据行项是否存在流程未结束的
     * @author：justin
     * @date：2019-11-14 10:54
     */
    @Select("select count(1) from repair_order_item ri inner join repair_order_head rh on ri.order_no=rh.order_no  " +
            " where rh.id=#{orderId} and ri.status!='003' and ri.deal_state!=#{dealState}")
    @ResultType(Integer.class)
    public Integer checkItemStatusByOrderId(@Param("orderId") String orderId, @Param("dealState") String dealState);

    /**
     * @description：批量更新行项目状态
     * @author：justin
     * @date：2019-11-27 15:53
     */
    public void updateOrderItemStatusBatch(List<TRepairOrderItem> itemList);

    /**
     * @description：维修记录物料明细
     * @author：justin
     * @date：2019-12-05 10:08
     */
    public List<TRepairOrderItem> repairOrderDetailList(RepairOrderItem item);

    /**
     * @description：根据单号删除行项目
     * @author：justin
     * @date：2019-12-11 16:59
     */
    @Delete("delete from repair_order_item where order_no=#{orderNo}")
    public void deleteOrderItemByOrderNo(@Param("orderNo") String orderNo);

    /**
     * @description：分页查询维修物料信息
     * @author：justin
     * @date：2020-01-16 09:36
     */
    public List<TRepairOrderItem> queryRepairItemList(RepairOrderItem info);
}
