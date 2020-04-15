package com.fl.web.service.impl.mdm;

import com.fl.web.dao.mdm.IAttachInfoDao;
import com.fl.web.entity.mdm.TAttachInfo;
import com.fl.web.service.mdm.IAttachInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：AttachInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-05 17:04
 */
@Service
@Transactional
public class AttachInfoServiceImpl implements IAttachInfoService {
    @Autowired
    private IAttachInfoDao attachInfoDao;

    @Override
    public void saveAttachInfoBatch(List<TAttachInfo> list) {
        attachInfoDao.saveAttachInfoBatch(list);
    }

    @Override
    public void saveAttachInfo(TAttachInfo info) {
        attachInfoDao.saveAttachInfo(info);
    }

    @Override
    public TAttachInfo getAttachInfoById(String id) {
        return attachInfoDao.getAttachInfoById(id);
    }

    @Override
    public void deleteAttachInfoById(String id) {
        attachInfoDao.deleteAttachInfoById(id);
    }

    @Override
    public List<TAttachInfo> getAttachInfoByOrderNo(String orderNo) {
        return attachInfoDao.getAttachInfoByOrderNo(orderNo);
    }

    @Override
    public List<TAttachInfo> getAttachInfoByTableId(String tableId) {
        return attachInfoDao.getAttachInfoByTableId(tableId);
    }

    @Override
    public Integer checkAttachInfoByTableId(String tableId, String flag) {
        return attachInfoDao.checkAttachInfoByTableId(tableId, flag);
    }
}
