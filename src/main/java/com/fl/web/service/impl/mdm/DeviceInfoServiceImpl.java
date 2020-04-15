package com.fl.web.service.impl.mdm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.IDeviceInfoDao;
import com.fl.web.entity.mdm.TDeviceInfo;
import com.fl.web.model.mdm.DeviceInfo;
import com.fl.web.service.mdm.IDeviceInfoService;
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
 * @类名称：DeviceInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 16:04
 */
@Service
@Transactional
public class DeviceInfoServiceImpl implements IDeviceInfoService {
    @Autowired
    private IDeviceInfoDao deviceInfoDao;

    @Override
    public PageInfo<TDeviceInfo> queryDeviceInfoList(DeviceInfo info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TDeviceInfo> list = deviceInfoDao.queryDeviceInfoList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void saveDeviceInfo(TDeviceInfo info) {
        info.setId(UUIDUtil.get32UUID());
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        info.setStatus(StaticParam.ADD);
        deviceInfoDao.saveDeviceInfo(info);
    }

    @Override
    public TDeviceInfo getDeviceInfoBySN(String sn) {
        return deviceInfoDao.getDeviceInfoBySN(sn);
    }

    @Override
    public TDeviceInfo getDeviceInfoById(String id) {
        return deviceInfoDao.getDeviceInfoById(id);
    }

    @Override
    public void deleteDeviceInfoById(String id) {
        TDeviceInfo info = new TDeviceInfo();
        info.setId(id);
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        info.setStatus(StaticParam.DEL);
        deviceInfoDao.updateDeviceInfo(info);
    }

    @Override
    public void updateDeviceInfo(TDeviceInfo info) {
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        info.setStatus(StaticParam.MOD);
        deviceInfoDao.updateDeviceInfo(info);
    }

    @Override
    public List<TDeviceInfo> searchDeviceInfoList(String info) {
        return deviceInfoDao.searchDeviceInfoList(info);
    }

    @Override
    public List<TDeviceInfo> findAllDeviceInfoList() {
        return deviceInfoDao.findAllDeviceInfoList();
    }
}
