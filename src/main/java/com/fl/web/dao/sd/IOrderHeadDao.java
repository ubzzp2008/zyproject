package com.fl.web.dao.sd;

import com.fl.web.entity.sd.TOrderHead;
import com.fl.web.model.sd.OrderHead;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:04
 */
public interface IOrderHeadDao {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-16 17:23
     */
    public List<TOrderHead> queryOrderList(OrderHead info);

    /**
     * @description：保存申请单头信息
     * @author：justin
     * @date：2019-10-16 18:03
     */
    public void saveOrderHead(TOrderHead info);

    /**
     * @description：根据单号获取头信息
     * @author：justin
     * @date：2019-10-16 18:18
     */
    public TOrderHead getOrderHeadByOrderNo(String orderNo);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2019-12-25 12:16
     */
    public Integer checkOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus);

    /**
     * @description：批量检查数据状态
     * @author：justin
     * @date：2019-12-25 12:16
     */
    public Integer checkOrderStatusBatch(@Param("orderNoList") List<String> orderNoList, @Param("orderStatus") String orderStatus);

    /**
     * @description：更新状态
     * @author：justin
     * @date：2019-12-25 12:21
     */
    public void updateOrderStatus(Map<String, Object> param);
    /**
     * @description：批量更新状态
     * @author：justin
     * @date：2019-12-25 12:21
     */
    public void updateOrderStatusBatch(Map<String, Object> param);

    /**
     * @description：根据单号删除
     * @author：justin
     * @date：2019-12-30 09:34
     */
    @Delete("update order_head set status=#{status},update_by=#{updateBy},update_date=#{updateDate} where order_no=#{orderNo}")
    public void deleteOrderHead(Map<String, Object> param);

    /**
     * @description：修改采购订单
     * @author：justin
     * @date：2019-12-30 09:43
     */
    public void updateOrderHead(TOrderHead info);

    /**
     * @description：根据单号更新单据状态
     * @author：justin
     * @date：2020-01-08 14:22
     */
    public void updateOrderStatusByOrderNo(String value, String ord30);
}
