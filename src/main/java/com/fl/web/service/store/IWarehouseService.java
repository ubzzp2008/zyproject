package com.fl.web.service.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.store.TWarehouse;
import com.fl.web.model.store.Warehouse;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：IWarehouseServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:34
 */
public interface IWarehouseService {
    /**
     * @desc：分页查询
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public PageInfo<TWarehouse> queryWarehouseList(Warehouse info);

    /**
     * @desc：根据code获取数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public TWarehouse getWarehouseByCode(String code);

    /**
     * @desc：根据id获取数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public TWarehouse getWarehouseById(String id);

    /**
     * @desc：保存数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public void saveWarehouse(TWarehouse warehouse);

    /**
     * @desc：修改数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public void updateWarehouse(TWarehouse warehouse);

    /**
     * @desc：根据id删除数据
     * @author：justin
     * @date：2020/2/5 11:58
     */
    public void deleteWarehouseById(String id);

    /**
     * @desc：获取所有仓位
     * @author：justin
     * @date：2020/2/5 11:58
     */
    public List<TWarehouse> getWarehouseByCompany(String companyCode);
}
