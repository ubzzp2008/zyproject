package com.fl.web.service.impl.log;

import com.fl.web.dao.log.IProcessLogDao;
import com.fl.web.entity.log.TProcessLog;
import com.fl.web.service.log.IProcessLogService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderLogServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-23 16:00
 */
@Service
@Transactional
public class ProcessLogServiceImpl implements IProcessLogService {
    @Autowired
    private IProcessLogDao processLogDao;

    @Override
    public void saveProcessLog(TProcessLog processLog) {
        processLog.setId(UUIDUtil.get32UUID());
        processLog.setCreateBy(SessionUser.getUserName());
        processLog.setRealname(SessionUser.getRealName());
        processLog.setCreateDate(new Date());
        processLogDao.saveProcessLog(processLog);
    }

    @Override
    public void saveProcessLogBatch(List<String> orderNoList, String reqStatus, String desc, String reason) {
        //批量保存日志
        List<TProcessLog> logList = new ArrayList<>();
        for (String orderNo : orderNoList) {
            TProcessLog log = new TProcessLog(orderNo, reqStatus, desc, reason);
            log.setId(UUIDUtil.get32UUID());
            log.setCreateBy(SessionUser.getUserName());
            log.setRealname(SessionUser.getRealName());
            log.setCreateDate(new Date());
            logList.add(log);
        }
        processLogDao.saveProcessLogBatch(logList);
    }

    @Override
    public List<TProcessLog> getProcessLogList(String orderNo) {
        return processLogDao.getProcessLogList(orderNo);
    }

    @Override
    public void deleteProcessLogBatch(List<String> orderNoList) {
        processLogDao.deleteProcessLogBatch(orderNoList);
    }

    @Override
    public void deleteProcessLog(String orderNo) {
        processLogDao.deleteProcessLog(orderNo);
    }
}
