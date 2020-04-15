package com.fl.web.service.impl.mdm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.IKunnrInfoDao;
import com.fl.web.entity.mdm.TKunnrInfo;
import com.fl.web.model.mdm.KunnrInfo;
import com.fl.web.service.mdm.IKunnrInfoService;
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
 * @类名称：KunnrInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 12:46
 */
@Service
@Transactional
public class KunnrInfoServiceImpl implements IKunnrInfoService {
    @Autowired
    private IKunnrInfoDao kunnrInfoDao;

    @Override
    public PageInfo<TKunnrInfo> queryKunnrInfoList(KunnrInfo info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TKunnrInfo> list = kunnrInfoDao.queryKunnrInfoList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void saveKunnrInfo(TKunnrInfo info) {
        info.setId(UUIDUtil.get32UUID());
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        info.setStatus(StaticParam.ADD);
        kunnrInfoDao.saveKunnrInfo(info);
    }

    @Override
    public TKunnrInfo getKunnrInfoByKunnr(String kunnr) {
        return kunnrInfoDao.getKunnrInfoByKunnr(kunnr);
    }

    @Override
    public TKunnrInfo getKunnrInfoById(String id) {
        return kunnrInfoDao.getKunnrInfoById(id);
    }

    @Override
    public void deleteKunnrInfoById(String id) {
        TKunnrInfo info = new TKunnrInfo();
        info.setId(id);
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        info.setStatus(StaticParam.DEL);
        kunnrInfoDao.updateKunnrInfo(info);
    }

    @Override
    public void updateKunnrInfo(TKunnrInfo info) {
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        info.setStatus(StaticParam.MOD);
        kunnrInfoDao.updateKunnrInfo(info);
    }

    @Override
    public List<TKunnrInfo> searchKunnrInfoList(String info) {
        return kunnrInfoDao.searchKunnrInfoList(info);
    }
}
