package com.fl.web.dao.sd;

import com.fl.web.entity.sd.TOrderReq;
import com.fl.web.model.sd.OrderReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderReqDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-26 17:30
 */
public interface IOrderReqDao {
    /**
     * @description：批量保存
     * @author：justin
     * @date：2019-10-16 18:03
     */
    public void saveOrderReqBatch(List<TOrderReq> itemList);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-16 17:23
     */
    public List<TOrderReq> queryOrderReqList(OrderReq info);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-27 10:02
     */
    public TOrderReq getOrderReqById(String id);

    /**
     * @description：更新数据
     * @author：justin
     * @date：2019-12-27 10:11
     */
    public void updateOrderReq(TOrderReq info);

    /**
     * @description：根据id检查数据状态
     * @author：justin
     * @date：2019-12-23 16:29
     */
    public Integer checkReqStatus(@Param("id") String id, @Param("reqStatus") String reqStatus);

    /**
     * @description：根据idList检查数据状态
     * @author：justin
     * @date：2019-12-23 16:29
     */
    public Integer checkReqStatusBatch(@Param("idList") List<String> idList, @Param("reqStatus") String reqStatus);

    /**
     * @description：根据id单个更新
     * @author：justin
     * @date：2019-12-27 11:04
     */
    public void updateOrderReqStatus(Map<String, Object> param);

    /**
     * @description：批量更新
     * @author：justin
     * @date：2019-12-27 11:04
     */
    public void updateOrderReqStatusBatch(Map<String, Object> param);

    /**
     * @description：批量删除
     * @author：justin
     * @date：2019-12-27 11:08
     */
    public void deleteOrderReqBatch(List<String> idList);

    /**
     * @description：根据idList获取数据
     * @author：justin
     * @date：2019-12-27 15:06
     */
    public List<TOrderReq> queryOrderReqByIdList(List<String> reqIdList);

    /**
     * @description：回写采购订单号
     * @author：justin
     * @date：2019-12-27 15:47
     */
    public void updateOrderNoById(Map<String, Object> param);

    /**
     * @description：批量修改
     * @author：justin
     * @date：2020-01-02 10:57
     */
    public void updateOrderReqBatch(List<TOrderReq> list);
}
