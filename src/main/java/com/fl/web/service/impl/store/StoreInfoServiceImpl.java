package com.fl.web.service.impl.store;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.store.IStoreInfoDao;
import com.fl.web.entity.store.TStoreInfo;
import com.fl.web.model.store.StoreInfo;
import com.fl.web.service.store.IStoreInfoService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：StoreInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:35
 */
@Service
@Transactional
public class StoreInfoServiceImpl implements IStoreInfoService {
    @Autowired
    private IStoreInfoDao storeInfoDao;

    @Override
    public TStoreInfo getStoreInfoByMatnr(String matnr, String wareCode, String warePosCode) {
        return storeInfoDao.getStoreInfoByMatnr(matnr, wareCode, warePosCode);
    }

    @Override
    public void updateStoreInfo(TStoreInfo storeInfo) {
        storeInfo.setStatus(StaticParam.MOD);
        storeInfo.setUpdateBy(SessionUser.getUserName());
        storeInfo.setUpdateDate(new Date());
        storeInfoDao.updateStoreInfo(storeInfo);
    }

    @Override
    public void saveStoreInfo(TStoreInfo storeInfo) {
        storeInfo.setId(UUIDUtil.get32UUID());
        storeInfo.setFrozenNum(new BigDecimal(0));
        storeInfo.setStatus(StaticParam.ADD);
        storeInfo.setCreateBy(SessionUser.getUserName());
        storeInfo.setCreateDate(new Date());
        storeInfoDao.saveStoreInfo(storeInfo);
    }

    @Override
    public PageInfo<TStoreInfo> queryStoreInfoList(StoreInfo info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TStoreInfo> list = storeInfoDao.queryStoreInfoList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public Integer checkStoreInfoNum(String matnr, String wareCode, String warePosCode, BigDecimal usableNum) {
        return storeInfoDao.checkStoreInfoNum(matnr, wareCode, warePosCode, usableNum);
    }
}
