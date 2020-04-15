package com.fl.web.service.saleServer;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TAttachInfo;
import com.fl.web.entity.saleServer.TRepairOrderHead;
import com.fl.web.entity.saleServer.TRepairOrderItem;
import com.fl.web.model.saleServer.RepairOrderHead;
import com.fl.web.model.saleServer.RepairOrderItem;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IDeviceRepairService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:25
 */
public interface IRepairOrderService {
    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-10-15 08:44
     */
    public String saveRepairOrder(TRepairOrderHead info, List<TAttachInfo> list);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-15 09:16
     */
    public PageInfo<TRepairOrderHead> queryRepairOrderList(RepairOrderHead info);

    /**
     * @description：批量更新维修单状态
     * @author：justin
     * @date：2019-10-15 14:39
     */
    public void updateRepairOrderStatusBatch(List<String> idList, String orderStatus, String applyNote, String reason);

    /**
     * @description：维修员退回报修单
     * @author：justin
     * @date：2019-10-16 20:18
     */
    public void toBackRepairOrder(String id, String orderNo);

    /**
     * @description：根据id和状态获取数据
     * @author：justin
     * @date：2019-10-17 08:46
     */
    public List<TRepairOrderHead> findDataByIdAndStatus(List<String> idList, String orderStatus);

    /**
     * @description：根据单号获取单据详情
     * @author：justin
     * @date：2019-10-25 15:43
     */
    public TRepairOrderHead getRepairOrderByOrderNo(String orderNo);

    /**
     * @description：保存单据行项目信息
     * @author：justin
     * @date：2019-10-25 16:13
     */
    public void saveRepairOrderItem(String orderId, String orderNo, List<TRepairOrderItem> itemList);

    /**
     * @description：维修员确认工单处理完毕
     * @author：justin
     * @date：2019-11-14 10:06
     */
    public void updateRepairOrderFinish(String orderId);

    /**
     * @description：检查单据行项是否存在流程未结束的
     * @author：justin
     * @date：2019-11-14 10:54
     */
    public Integer checkItemStatusByOrderId(String orderId);

    /**
     * @description：维修方案审批通过
     * @author：justin
     * @date：2019-11-27 15:38
     */
    public void saveApplyRepairPlan(List<String> idList, String reason);

    /**
     * @description：维修单跟踪报表导出
     * @author：justin
     * @date：2019-12-01 13:31
     */
    public List<TRepairOrderHead> exportRepairOrder(RepairOrderHead info);

    /**
     * @description：查询维修记录单据
     * @author：justin
     * @date：2019-12-01 14:03
     */
    public PageInfo<TRepairOrderHead> repairOrderByKunnrList(RepairOrderHead info);

    /**
     * @description：维修记录物料明细表
     * @author：justin
     * @date：2019-12-05 10:18
     */
    public PageInfo<TRepairOrderItem> repairOrderDetailList(RepairOrderItem info);

    /**
     * @description：报修单指派操作
     * @author：justin
     * @date：2019-12-11 16:00
     */
    public void assignRepairOrder(List<String> idList, String userName, String realName, String reason);

    /**
     * @description：领取报修单
     * @author：justin
     * @date：2019-12-11 16:05
     */
    public void receiveRepairOrder(List<String> idList);

    /**
     * @description：作废维修单
     * @author：justin
     * @date：2019-12-23 11:15
     */
    public void cancelRepairOrder(String orderId, String reason);

    /**
     * @description：分页查询维修物料信息
     * @author：justin
     * @date：2020-01-16 09:35
     */
    public PageInfo<TRepairOrderItem> queryRepairItemList(RepairOrderItem info);
}
