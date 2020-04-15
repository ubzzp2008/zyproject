package com.fl.web.service.impl.mdm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.IQualityInfoDao;
import com.fl.web.entity.mdm.TQualityInfo;
import com.fl.web.model.mdm.QualityInfo;
import com.fl.web.service.mdm.IQualityInfoService;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：QualityInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 10:29
 */
@Service
@Transactional
public class QualityInfoServiceImpl implements IQualityInfoService {
    @Autowired
    private IQualityInfoDao qualityInfoDao;

    @Override
    public PageInfo<TQualityInfo> queryQualityInfoList(QualityInfo info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TQualityInfo> infoList = qualityInfoDao.queryQualityInfoList(info);
        PageInfo result = new PageInfo(infoList);
        return result;
    }

    @Override
    public void saveQualityInfo(TQualityInfo info) {
        info.setId(UUIDUtil.get32UUID());
        qualityInfoDao.saveQualityInfo(info);
    }

    @Override
    public void updateQualityInfo(TQualityInfo info) {
        qualityInfoDao.updateQualityInfo(info);
    }

    @Override
    public void deleteQualityInfoById(String id) {
        qualityInfoDao.deleteQualityInfoById(id);
    }

    @Override
    public List<TQualityInfo> getAllQualityInfo() {
        return qualityInfoDao.queryQualityInfoList(new QualityInfo());
    }

    @Override
    public TQualityInfo getQualityInfoById(String id) {
        return qualityInfoDao.getQualityInfoById(id);
    }

    @Override
    public TQualityInfo getQualityInfoByCode(String code) {
        return qualityInfoDao.getQualityInfoByCode(code);
    }
}
