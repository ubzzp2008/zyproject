package com.fl.web.service.log;

import com.fl.web.entity.log.TProcessLog;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderLogService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-23 16:00
 */
public interface IProcessLogService {
    /**
     * @description：保存采购订单日志
     * @author：justin
     * @date：2019-12-23 15:59
     */
    public void saveProcessLog(TProcessLog processLog);

    /**
     * @description：批量保存采购订单日志
     * @author：justin
     * @date：2019-12-23 15:59
     */
    public void saveProcessLogBatch(List<String> orderNoList, String reqStatus, String desc, String reason);

    /**
     * @description：根据单号获取日志信息
     * @author：justin
     * @date：2019-12-23 15:59
     */
    public List<TProcessLog> getProcessLogList(String orderNo);

    /**
     * @description：物理删除日志信息
     * @author：justin
     * @date：2019-12-24 10:48
     */
    public void deleteProcessLogBatch(List<String> orderNoList);

    /**
     * @description：物理删除日志信息
     * @author：justin
     * @date：2019-12-24 10:48
     */
    public void deleteProcessLog(String orderNo);
}
