package com.fl.web.service.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.store.TStoreOrder;
import com.fl.web.model.store.StoreOrder;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IStoreOrderService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:39
 */
public interface IStoreOrderService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-02-20 18:29
     */
    public PageInfo<TStoreOrder> queryStoreOrderList(StoreOrder info);

    /**
     * @description：质检和免检入库保存数据
     * @author：justin
     * @param：censorFlag质检入库1，免检入库0
     * @date：2020-02-13 14:17
     */
    public String addStoreOrder(TStoreOrder info, String censorFlag);

    /**
     * @description：根据仓位获取数据
     * @author：justin
     * @date：2020-02-17 11:50
     */
    public Integer findStoreOrderByWarePosCode(String warePosCode);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2020-02-20 16:57
     */
    public String saveOutStoreOrder(TStoreOrder info);

    /**
     * @description：根据id检查数据状态
     * @author：justin
     * @date：2020-02-20 16:23
     */
    public Integer checkStoreOrderStatus(String id, String state);

    /**
     * @description：根据idList检查数据状态
     * @author：justin
     * @date：2020-02-20 16:23
     */
    public Integer checkStoreOrderStatusBatch(List<String> idList, String state);

    /**
     * @description：批量更新单据状态
     * @author：justin
     * @date：2020-02-20 16:42
     */
    public void updateStoreOrderStatusBatch(List<String> idList, String state);

    /**
     * @description：根据单号获取详情
     * @author：justin
     * @date：2020-02-21 10:12
     */
    public TStoreOrder getStoreOrderByStoreNo(String storeNo);
}
