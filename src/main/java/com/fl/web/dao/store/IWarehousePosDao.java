package com.fl.web.dao.store;

import com.fl.web.entity.store.TWarehousePos;
import com.fl.web.model.store.WarehousePos;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IWarehousePosDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 10:53
 */
public interface IWarehousePosDao {
    /**
     * @desc：分页查询
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public List<TWarehousePos> queryWarehousePosList(WarehousePos warehousePos);

    /**
     * @desc：修改数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public void updateWarehousePos(TWarehousePos warehousePos);

    /**
     * @desc：保存数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public void saveWarehousePos(TWarehousePos warehousePos);

    /**
     * @desc：根据id获取数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public TWarehousePos getWarehousePosById(String id);

    /**
     * @desc：根据code获取数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public TWarehousePos getWarehousePosByCode(String code);

    /**
     * @description：根据仓库编码获取仓位列表
     * @author：justin
     * @date：2020-02-13 14:38
     */
    @Select("select code,name from warehouse_pos where ware_code=#{wareCode} and project_code=#{projectCode} and status!='003'")
    @ResultType(TWarehousePos.class)
    public List<TWarehousePos> getWarehousePosList(@Param("wareCode") String wareCode, @Param("projectCode") String projectCode);

    /**
     * @description：根据仓库编码获取仓位列表
     * @author：justin
     * @date：2020-02-14 14:05
     */
    @Select("select code,name from warehouse_pos where ware_code=#{wareCode} and status!='003'")
    @ResultType(TWarehousePos.class)
    public List<TWarehousePos> getWarehousePosByWareCode(@Param("wareCode") String wareCode);
}
