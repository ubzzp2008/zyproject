package com.fl.web.service.impl.store;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.store.IWarehousePosDao;
import com.fl.web.entity.store.TWarehousePos;
import com.fl.web.model.store.WarehousePos;
import com.fl.web.service.store.IWarehousePosService;
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
public class WarehousePosServiceImpl implements IWarehousePosService {
    @Autowired
    private IWarehousePosDao warehousePosDao;

    @Override
    public PageInfo<TWarehousePos> queryWarehousePosList(WarehousePos info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TWarehousePos> list = warehousePosDao.queryWarehousePosList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public TWarehousePos getWarehousePosByCode(String code) {
        return warehousePosDao.getWarehousePosByCode(code);
    }

    @Override
    public TWarehousePos getWarehousePosById(String id) {
        return warehousePosDao.getWarehousePosById(id);
    }

    @Override
    public void saveWarehousePos(TWarehousePos warehouse) {
        warehouse.setId(UUIDUtil.get32UUID());
        warehouse.setStatus(StaticParam.ADD);
        warehouse.setCreateBy(SessionUser.getUserName());
        warehouse.setCreateDate(new Date());
        warehousePosDao.saveWarehousePos(warehouse);
    }

    @Override
    public void updateWarehousePos(TWarehousePos warehouse) {
        warehouse.setStatus(StaticParam.MOD);
        warehouse.setUpdateBy(SessionUser.getUserName());
        warehouse.setUpdateDate(new Date());
        warehousePosDao.updateWarehousePos(warehouse);
    }

    @Override
    public void deleteWarehousePosById(String id) {
        TWarehousePos warehouse = new TWarehousePos();
        warehouse.setId(id);
        warehouse.setStatus(StaticParam.DEL);
        warehouse.setUpdateBy(SessionUser.getUserName());
        warehouse.setUpdateDate(new Date());
        warehousePosDao.updateWarehousePos(warehouse);
    }

    @Override
    public List<TWarehousePos> getWarehousePosList(String wareCode, String projectCode) {
        return warehousePosDao.getWarehousePosList(wareCode, projectCode);
    }

    @Override
    public List<TWarehousePos> getWarehousePosByWareCode(String wareCode) {
        return warehousePosDao.getWarehousePosByWareCode(wareCode);
    }
}
