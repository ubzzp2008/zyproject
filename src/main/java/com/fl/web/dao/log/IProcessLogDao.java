package com.fl.web.dao.log;

import com.fl.web.entity.log.TProcessLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderLogDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-23 15:49
 */
public interface IProcessLogDao {

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
    public void saveProcessLogBatch(List<TProcessLog> logList);

    /**
     * @description：根据单号获取日志信息
     * @author：justin
     * @date：2019-12-23 15:59
     */
    public List<TProcessLog> getProcessLogList(String orderNo);

    /**
     * @description：物理删除日志信息
     * @author：justin
     * @date：2019-12-24 10:49
     */
    public void deleteProcessLogBatch(List<String> orderNoList);

    /**
     * @description：删除日志信息
     * @author：justin
     * @date：2019-12-30 09:26
     */
    @Delete("delete from process_log where order_no=#{orderNo}")
    public void deleteProcessLog(@Param("orderNo") String orderNo);
}
