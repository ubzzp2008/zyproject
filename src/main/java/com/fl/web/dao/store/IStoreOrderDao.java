package com.fl.web.dao.store;

import com.fl.web.entity.store.TStoreOrder;
import com.fl.web.model.store.StoreOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IStoreOrderDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:40
 */
public interface IStoreOrderDao {

    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-02-20 18:30
     */
    public List<TStoreOrder> queryStoreOrderList(StoreOrder info);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2020-02-13 14:26
     */
    public void saveStoreOrder(TStoreOrder info);

    /**
     * @description：根据仓位获取数据
     * @author：justin
     * @date：2020-02-17 11:51
     */
    @Select("select count(1) from store_order where ware_pos_code=#{warePosCode} and status!='003'")
    @ResultType(Integer.class)
    public Integer findStoreOrderByWarePosCode(@Param("warePosCode") String warePosCode);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-02-20 12:16
     */
    public Integer checkStoreOrderStatus(@Param("id") String id, @Param("state") String state);

    /**
     * @description：批量检查数据状态
     * @author：justin
     * @date：2020-02-20 12:16
     */
    public Integer checkStoreOrderStatusBatch(@Param("idList") List<String> idList, @Param("state") String state);

    /**
     * @description：根据id更新状态
     * @author：justin
     * @date：2020-02-20 18:25
     */
    public void updateStoreOrderStatus(Map<String, Object> param);

    /**
     * @description：批量更新
     * @author：justin
     * @date：2020-02-20 18:25
     */
    public void updateStoreOrderStatusBatch(Map<String, Object> param);

    /**
     * @description：根据单号获取数据
     * @author：justin
     * @date：2020-02-21 10:15
     */
    public TStoreOrder getStoreOrderByStoreNo(@Param("storeNo") String storeNo);
}
