package com.fl.web.dao.sd;

import com.fl.web.entity.sd.TOrderDelivery;
import com.fl.web.model.sd.OrderDelivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderDeliveryDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-08 09:41
 */
public interface IOrderDeliveryDao {

    /**
     * @description：批量保存
     * @author：justin
     * @date：2020-01-08 09:43
     */
    public void saveOrderDeliveryBatch(List<TOrderDelivery> deliveryList);

    /**
     * @description：分页查询收货信息
     * @author：justin
     * @date：2020-01-09 14:12
     */
    public List<TOrderDelivery> queryOrderDeliveryList(OrderDelivery info);

    /**
     * @description：根据id检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkDeliveryStatus(@Param("id") String id, @Param("state") String state);

    /**
     * @description：根据idList检查数据状态
     * @author：justin
     * @date：2019-12-23 16:23
     */
    public Integer checkDeliveryStatusBatch(@Param("idList") List<String> idList, @Param("state") String state);

    /**
     * @description：批量更新数据
     * @author：justin
     * @date：2020-01-21 15:18
     */
    public void updateOrderDeliveryBatch(List<TOrderDelivery> infoList);

    /**
     * @desc：更新收货数据
     * @author：justin
     * @date：2020/2/4 10:02
     */
    public void updateOrderDelivery(TOrderDelivery delivery);

    /**
     * @description：根据id批量修改数据
     * @author：justin
     * @date：2020-02-14 09:40
     */
    public void updateDeliveryStatusBatch(Map<String, Object> map);
}
