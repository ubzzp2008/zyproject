package com.fl.web.service.impl.saleServer;

import com.fl.web.dao.saleServer.IRepairOrderLogDao;
import com.fl.web.entity.saleServer.TRepairOrderLog;
import com.fl.web.service.saleServer.IRepairOrderLogService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RepairOrderLogServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-10 18:03
 */
@Service
@Transactional
public class RepairOrderLogServiceImpl implements IRepairOrderLogService {
    @Autowired
    private IRepairOrderLogDao repairOrderLogDao;

    @Override
    public void saveRepairOrderLog(String orderId, String applyNote, String reason) {
        //根据orderId获取单据信息
        TRepairOrderLog log = repairOrderLogDao.getLogInfoByOrderId(orderId);
        log.setId(UUIDUtil.get32UUID());
        log.setRealname(SessionUser.getRealName());
        log.setDescription(applyNote);
        log.setReason(reason);
        log.setCreateBy(SessionUser.getUserName());
        log.setCreateDate(new Date());
        repairOrderLogDao.saveRepairOrderLog(log);
    }

    @Override
    public void saveRepairOrderLogBatch(List<String> orderIdList, String applyNote, String reason) {
        //根据orderId获取单据信息
        List<TRepairOrderLog> logList = repairOrderLogDao.getLogInfoByOrderIdList(orderIdList);
        for (TRepairOrderLog log : logList) {
            log.setId(UUIDUtil.get32UUID());
            log.setRealname(SessionUser.getRealName());
            log.setDescription(applyNote);
            log.setReason(reason);
            log.setCreateBy(SessionUser.getUserName());
            log.setCreateDate(new Date());
        }
        repairOrderLogDao.saveRepairOrderLogBatch(logList);
    }

    @Override
    public List<TRepairOrderLog> getOrderLogListByOrderNo(String orderNo) {
        return repairOrderLogDao.getOrderLogListByOrderNo(orderNo);
    }
}
