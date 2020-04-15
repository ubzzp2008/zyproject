package com.fl.web.dao.saleServer;

import com.fl.web.entity.saleServer.TRepairOrderHead;
import com.fl.web.model.saleServer.RepairOrderHead;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IDeviceRepairDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:25
 */
public interface IRepairOrderHeadDao {
    /**
     * @description：保存维修单头信息
     * @author：justin
     * @date：2019-10-15 08:58
     */
    public void saveRepairOrderHead(TRepairOrderHead info);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-15 09:18
     */
    public List<TRepairOrderHead> queryRepairOrderList(RepairOrderHead info);

    /**
     * @description：批量更新维修单状态
     * @author：justin
     * @date：2019-10-15 17:53
     */
    public void updateRepairOrderStatusBatch(Map<String, Object> map);

    /**
     * @description：更新单据状态
     * @author：justin
     * @date：2019-11-01 12:38
     */
    public void updateRepairOrderStatusById(Map<String, Object> param);

    /**
     * @description：维修员退回报修单
     * @author：justin
     * @date：2019-10-16 20:20
     */
    public void toBackRepairOrder(Map<String, Object> map);

    /**
     * @description：根据id和状态获取数据
     * @author：justin
     * @date：2019-10-17 08:47
     */
    public List<TRepairOrderHead> findDataByIdAndStatus(Map<String, Object> param);

    /**
     * @description：根据单据号获取单据头信息
     * @author：justin
     * @date：2019-10-25 15:46
     */
    public TRepairOrderHead getOrderHeadByOrderNo(String orderNo);
    /**
     * @description：根据单据号获取单据头信息
     * @author：justin
     * @date：2019-10-25 15:46
     */
    public TRepairOrderHead getOrderHeadById(String id);


    /**
     * @description：查询维修记录
     * @author：justin
     * @date：2019-12-01 14:05
     */
    public List<TRepairOrderHead> repairOrderByKunnrList(RepairOrderHead info);
}
