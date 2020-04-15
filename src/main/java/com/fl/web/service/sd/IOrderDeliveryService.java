package com.fl.web.service.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.sd.TOrderDelivery;
import com.fl.web.model.sd.OrderDelivery;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderDeliveryService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-08 09:40
 */
public interface IOrderDeliveryService {
    /**
     * @description：分页查询已收货信息
     * @author：justin
     * @date：2020-01-09 14:10
     */
    public PageInfo<TOrderDelivery> queryOrderDeliveryList(OrderDelivery info);

    /**
     * @description：根据id检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkDeliveryStatus(String id, String state);

    /**
     * @description：根据idList检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkDeliveryStatusBatch(List<String> idList, String state);

    /**
     * @description：根据id批量删除收货数据
     * @author：justin
     * @date：2020-01-21 15:03
     */
    public void deleteOrderDelivery(TOrderDelivery delivery);

    /**
     * @description：收货确认操作
     * @author：justin
     * @date：2020-01-21 15:06
     */
    public void comfirmDeliveryToQM(List<TOrderDelivery> infoList);

    /**
     * @desc：确认将不送检物料转入库
     * @author：justin
     * @date：2020/2/5 17:03
     */
    public void comfirmPDToStore(List<String> idList, String state);
}
