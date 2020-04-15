package com.fl.web.dao.store;

import com.fl.web.entity.store.TWarehouse;
import com.fl.web.model.store.Warehouse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：IWarehouseDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:27
 */
public interface IWarehouseDao {
    /**
     * @desc：分页查询
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public List<TWarehouse> queryWarehouseList(Warehouse warehouse);

    /**
     * @desc：修改数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public void updateWarehouse(TWarehouse warehouse);

    /**
     * @desc：保存数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public void saveWarehouse(TWarehouse warehouse);

    /**
     * @desc：根据id获取数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public TWarehouse getWarehouseById(String id);

    /**
     * @desc：根据code获取数据
     * @author：justin
     * @date：2020/2/5 12:05
     */
    public TWarehouse getWarehouseByCode(String code);

    /**
     * @description：根据公司获取仓库列表
     * @author：justin
     * @date：2020-02-14 13:56
     */
    @Select("select code,name from warehouse where company_code=#{companyCode} and status!='003' order by code")
    @ResultType(TWarehouse.class)
    public List<TWarehouse> getWarehouseByCompany(@Param("companyCode") String companyCode);
}
