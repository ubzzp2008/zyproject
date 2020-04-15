package com.fl.web.service.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.store.TWarehousePos;
import com.fl.web.model.store.WarehousePos;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：IWarehousePosService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:34
 */
public interface IWarehousePosService {
    /**
     * @desc：分页查询
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public PageInfo<TWarehousePos> queryWarehousePosList(WarehousePos info);

    /**
     * @desc：根据code获取数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public TWarehousePos getWarehousePosByCode(String code);

    /**
     * @desc：根据id获取数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public TWarehousePos getWarehousePosById(String id);

    /**
     * @desc：保存数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public void saveWarehousePos(TWarehousePos warehousePos);

    /**
     * @desc：修改数据
     * @author：justin
     * @date：2020/2/5 11:57
     */
    public void updateWarehousePos(TWarehousePos warehousePos);

    /**
     * @desc：根据id删除数据
     * @author：justin
     * @date：2020/2/5 11:58
     */
    public void deleteWarehousePosById(String id);

    /**
     * @desc：根据仓库编码获取所有仓位
     * @author：justin
     * @date：2020/2/5 11:58
     */
    public List<TWarehousePos> getWarehousePosList(String wareCode, String projectCode);

    /**
     * @description：根据仓库编码获取仓位列表
     * @author：justin
     * @date：2020-02-14 14:04
     */
    public List<TWarehousePos> getWarehousePosByWareCode(String wareCode);
}
