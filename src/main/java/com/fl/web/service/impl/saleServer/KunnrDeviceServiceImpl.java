package com.fl.web.service.impl.saleServer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.saleServer.IKunnrDeviceDao;
import com.fl.web.entity.saleServer.TKunnrDevice;
import com.fl.web.model.saleServer.KunnrDevice;
import com.fl.web.service.saleServer.IKunnrDeviceService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：KunnrDeviceServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 09:31
 */
@Service
@Transactional
public class KunnrDeviceServiceImpl implements IKunnrDeviceService {
    @Autowired
    private IKunnrDeviceDao kunnrDeviceDao;

    @Override
    public PageInfo<TKunnrDevice> queryKunnrDeviceList(KunnrDevice info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TKunnrDevice> list = kunnrDeviceDao.queryKunnrDeviceList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void deleteKunnrDeviceById(String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("status", StaticParam.DEL);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        kunnrDeviceDao.deleteKunnrDevice(map);
    }

    @Override
    public void saveKunnrDevice(TKunnrDevice info) {
        info.setId(UUIDUtil.get32UUID());
        info.setFlag("0");//默认启用
        info.setStatus(StaticParam.ADD);
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        kunnrDeviceDao.saveKunnrDevice(info);
    }

    @Override
    public TKunnrDevice checkDataExist(String kunnr, String deviceSN) {
        return kunnrDeviceDao.checkDataExist(kunnr, deviceSN);
    }

    @Override
    public List<TKunnrDevice> searchDeviceListByKunnr(String info, String kunnr) {
        return kunnrDeviceDao.searchDeviceListByKunnr(info, kunnr);
    }

    @Override
    public List<TKunnrDevice> findDeviceListByKunnr(String kunnr) {
        return kunnrDeviceDao.findDeviceListByKunnr(kunnr);
    }

    @Override
    public void updateKunnrDeviceFlag(String id, String flag) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("flag", flag);
        map.put("status", StaticParam.MOD);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        kunnrDeviceDao.updateKunnrDeviceFlag(map);
    }
}
