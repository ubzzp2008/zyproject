package com.fl.web.dao.saleServer;

import com.fl.web.entity.saleServer.TRepairOrderLog;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRepairOrderLogDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-10 18:04
 */
public interface IRepairOrderLogDao {
    /**
     * @description：保存维修单日志信息
     * @author：justin
     * @date：2019-12-10 18:09
     */
    public void saveRepairOrderLog(TRepairOrderLog repairOrderLog);

    /**
     * @description：批量保存日志信息
     * @author：justin
     * @date：2019-12-11 10:16
     */
    public void saveRepairOrderLogBatch(List<TRepairOrderLog> logList);

    /**
     * @description：根据单据号获取数据
     * @author：justin
     * @date：2019-12-11 10:07
     */
    public TRepairOrderLog getLogInfoByOrderId(String orderId);

    /**
     * @description：根据单据号获取数据
     * @author：justin
     * @date：2019-12-11 10:07
     */
    public List<TRepairOrderLog> getLogInfoByOrderIdList(List<String> orderIdList);

    /**
     * @description：根据单号获取日志信息
     * @author：justin
     * @date：2019-12-16 11:36
     */
    public List<TRepairOrderLog> getOrderLogListByOrderNo(String orderNo);
}
