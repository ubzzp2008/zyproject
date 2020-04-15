package com.fl.web.service.saleServer;

import com.fl.web.entity.saleServer.TRepairOrderLog;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRepairOrderLogService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-10 18:04
 */
public interface IRepairOrderLogService {

    /**
     * @description：保存维修单日志信息
     * @author：justin
     * @date：2019-12-10 18:09
     */
    public void saveRepairOrderLog(String orderId, String applyNote, String reason);

    /**
     * @description：批量保存维修单日志信息
     * @author：justin
     * @date：2019-12-10 18:09
     */
    public void saveRepairOrderLogBatch(List<String> orderIdList, String applyNote, String reason);

    /**
     * @description：根据单号获取日志信息
     * @author：justin
     * @date：2019-12-16 11:36
     */
    public List<TRepairOrderLog> getOrderLogListByOrderNo(String orderNo);
}
