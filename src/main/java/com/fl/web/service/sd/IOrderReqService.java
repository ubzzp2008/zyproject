package com.fl.web.service.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.sd.TOrderReq;
import com.fl.web.entity.sd.TOrderReqModel;
import com.fl.web.model.sd.OrderReq;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderReqService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:10
 */
public interface IOrderReqService {
    /**
     * @description：保存采购申请
     * @author：justin
     * @date：2019-10-16 17:50
     */
    public void saveOrderReq(TOrderReqModel info);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-16 17:18
     */
    public PageInfo<TOrderReq> queryOrderReqList(OrderReq info);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-27 10:01
     */
    public TOrderReq getOrderReqById(String id);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-27 10:01
     */
    public TOrderReq getOrderReqDetailById(String id);

    /**
     * @description：修改并保存数据
     * @author：justin
     * @date：2019-12-23 09:04
     */
    public void updateSaveOrderReq(TOrderReq info);

    /**
     * @description：根据id检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkReqStatus(String id, String reqStatus);

    /**
     * @description：根据idList检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkReqStatusBatch(List<String> idList, String reqStatus);

    /**
     * @description：批量更新单据状态
     * @author：justin
     * @date：2019-12-23 16:42
     */
    public void updateOrderReqStatusBatch(List<String> idList, String reqStatus, String desc, String reason);

    /**
     * @description：批量物理删除数据
     * @author：justin
     * @date：2019-12-23 17:20
     */
    public void deleteOrderReqBatch(List<String> idList);

    /**
     * @description：根据idList获取数据
     * @author：justin
     * @date：2019-12-27 15:05
     */
    public List<TOrderReq> queryOrderReqByIdList(List<String> reqIdList);

    /**
     * @description：批量修改数据
     * @author：justin
     * @date：2020-01-02 10:53
     */
    public void updateOrderReqBatch(List<TOrderReq> list, String reqStatus, String desc, String reason);
}
