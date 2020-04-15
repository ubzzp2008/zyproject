package com.fl.web.service.impl.mdm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.ISupplierInfoDao;
import com.fl.web.entity.mdm.TSupplierInfo;
import com.fl.web.model.mdm.SupplierInfo;
import com.fl.web.service.mdm.ISupplierInfoService;
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
 * @项目名称：hcmanager
 * @类名称：SupplierInfoServiceimpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 11:50
 */
@Service
@Transactional
public class SupplierInfoServiceImpl implements ISupplierInfoService {
    @Autowired
    private ISupplierInfoDao supplierInfoDao;

    @Override
    public PageInfo<TSupplierInfo> querySupplierInfoList(SupplierInfo info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TSupplierInfo> list = supplierInfoDao.querySupplierInfoList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void saveSupplierInfo(TSupplierInfo info) {
        info.setId(UUIDUtil.get32UUID());
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        info.setStatus(StaticParam.ADD);
        supplierInfoDao.saveSupplierInfo(info);
    }

    @Override
    public TSupplierInfo getSupplierInfoByCode(String code) {
        return supplierInfoDao.getSupplierInfoByCode(code);
    }

    @Override
    public TSupplierInfo getSupplierInfoById(String id) {
        return supplierInfoDao.getSupplierInfoById(id);
    }

    @Override
    public void deleteSupplierInfoById(String id) {
        TSupplierInfo info = new TSupplierInfo();
        info.setId(id);
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        info.setStatus(StaticParam.DEL);
        supplierInfoDao.updateSupplierInfo(info);
    }

    @Override
    public void updateSupplierInfo(TSupplierInfo info) {
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        info.setStatus(StaticParam.MOD);
        supplierInfoDao.updateSupplierInfo(info);
    }

    @Override
    public List<TSupplierInfo> searchSupplierInfoList(String info) {
        return supplierInfoDao.searchSupplierInfoList(info);
    }
}
