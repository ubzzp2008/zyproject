package com.fl.web.service.impl.store;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.store.IWarehouseDao;
import com.fl.web.entity.store.TWarehouse;
import com.fl.web.model.store.Warehouse;
import com.fl.web.service.store.IWarehouseService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：WarehouseServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:35
 */
@Service
@Transactional
public class WarehouseServiceImpl implements IWarehouseService {
    @Autowired
    private IWarehouseDao warehouseDao;

    @Override
    public PageInfo<TWarehouse> queryWarehouseList(Warehouse info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TWarehouse> list = warehouseDao.queryWarehouseList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public TWarehouse getWarehouseByCode(String code) {
        return warehouseDao.getWarehouseByCode(code);
    }

    @Override
    public TWarehouse getWarehouseById(String id) {
        return warehouseDao.getWarehouseById(id);
    }

    @Override
    public void saveWarehouse(TWarehouse warehouse) {
        warehouse.setId(UUIDUtil.get32UUID());
        warehouse.setStatus(StaticParam.ADD);
        warehouse.setCreateBy(SessionUser.getUserName());
        warehouse.setCreateDate(new Date());
        warehouseDao.saveWarehouse(warehouse);
    }

    @Override
    public void updateWarehouse(TWarehouse warehouse) {
        warehouse.setStatus(StaticParam.MOD);
        warehouse.setUpdateBy(SessionUser.getUserName());
        warehouse.setUpdateDate(new Date());
        warehouseDao.updateWarehouse(warehouse);
    }

    @Override
    public void deleteWarehouseById(String id) {
        TWarehouse warehouse = new TWarehouse();
        warehouse.setId(id);
        warehouse.setStatus(StaticParam.DEL);
        warehouse.setUpdateBy(SessionUser.getUserName());
        warehouse.setUpdateDate(new Date());
        warehouseDao.updateWarehouse(warehouse);
    }

    @Override
    public List<TWarehouse> getWarehouseByCompany(String companyCode) {
        return warehouseDao.getWarehouseByCompany(companyCode);
    }
}
