package com.fl.web.service.impl.sd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.sd.IOrderReqDao;
import com.fl.web.entity.log.TProcessLog;
import com.fl.web.entity.sd.TOrderReq;
import com.fl.web.entity.sd.TOrderReqModel;
import com.fl.web.model.sd.OrderReq;
import com.fl.web.service.log.IProcessLogService;
import com.fl.web.service.sd.IOrderReqService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderItemServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:11
 */
@Service
@Transactional
public class OrderReqServiceImpl implements IOrderReqService {
    @Autowired
    private IOrderReqDao orderReqDao;
    @Autowired
    private IProcessLogService processLogService;

    @Override
    public PageInfo<TOrderReq> queryOrderReqList(OrderReq info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TOrderReq> list = orderReqDao.queryOrderReqList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void saveOrderReq(TOrderReqModel info) {
        //完善申请单行信息
        List<TOrderReq> reqList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for (TOrderReq item : info.getItemList()) {
            String id = UUIDUtil.get32UUID();
            idList.add(id);
            item.setId(id);
            item.setCompanyCode(info.getCompanyCode());
            item.setCompany(info.getCompany());
            item.setProjectCode(info.getProjectCode());
            item.setReqDept(info.getReqDept());
            item.setReqName(info.getReqName());
            item.setReqDate(info.getReqDate());
            item.setReqStatus(info.getReqStatus());
            item.setReqFlag(info.getReqFlag());
            item.setStatus(StaticParam.ADD);
            item.setCreateBy(SessionUser.getUserName());
            item.setCreateDate(new Date());
            reqList.add(item);
        }
        orderReqDao.saveOrderReqBatch(reqList);
        //保存日志，提交时才保存日志，保存时不保存日志
        if (StringUtil.equals(StaticParam.REQ_1, info.getReqStatus())) {
            processLogService.saveProcessLogBatch(idList, StaticParam.REQ_1, "提交询价申请", "提交询价申请");
        }
        if (StringUtil.equals(StaticParam.REQ_10, info.getReqStatus())) {
            processLogService.saveProcessLogBatch(idList, StaticParam.REQ_10, "提交采购申请", "提交采购申请");
        }
    }

    @Override
    public TOrderReq getOrderReqById(String id) {
        return orderReqDao.getOrderReqById(id);
    }

    @Override
    public TOrderReq getOrderReqDetailById(String id) {
        TOrderReq req = orderReqDao.getOrderReqById(id);
        if (req != null) {
            List<TProcessLog> logList = processLogService.getProcessLogList(id);
            req.setLogList(logList);
        }
        return req;
    }

    @Override
    public void updateSaveOrderReq(TOrderReq info) {
        //完善申请单头信息
        info.setStatus(StaticParam.MOD);
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        //更新信息
        orderReqDao.updateOrderReq(info);
        //保存日志，提交时才保存日志，保存时不保存日志
        if (StringUtil.equals(StaticParam.REQ_10, info.getReqStatus())) {
            processLogService.saveProcessLog(new TProcessLog(info.getId(), StaticParam.REQ_10, "提交采购申请", "提交采购申请"));
        }
    }

    @Override
    public Integer checkReqStatus(String id, String reqStatus) {
        return orderReqDao.checkReqStatus(id, reqStatus);
    }

    @Override
    public Integer checkReqStatusBatch(List<String> idList, String reqStatus) {
        return orderReqDao.checkReqStatusBatch(idList, reqStatus);
    }

    @Override
    public void updateOrderReqStatusBatch(List<String> idList, String reqStatus, String desc, String reason) {
        Map<String, Object> param = new HashMap<>();
        param.put("idList", idList);
        param.put("reqStatus", reqStatus);
        if (StringUtil.equals(StaticParam.REQ_4, reqStatus)) {
            //询价申请领取，记录领取人
            param.put("enquiryBy", SessionUser.getUserName());
        }
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        param.put("status", StaticParam.MOD);
        orderReqDao.updateOrderReqStatusBatch(param);
        //批量保存日志
        processLogService.saveProcessLogBatch(idList, reqStatus, desc, reason);
    }

    @Override
    public void deleteOrderReqBatch(List<String> idList) {
        orderReqDao.deleteOrderReqBatch(idList);
        //删除日志信息
        processLogService.deleteProcessLogBatch(idList);
    }

    @Override
    public List<TOrderReq> queryOrderReqByIdList(List<String> reqIdList) {
        return orderReqDao.queryOrderReqByIdList(reqIdList);
    }

    @Override
    public void updateOrderReqBatch(List<TOrderReq> list, String reqStatus, String desc, String reason) {
        List<String> idList = new ArrayList<>();
        for (TOrderReq req : list) {
            req.setReqStatus(reqStatus);
            req.setStatus(StaticParam.MOD);
            req.setUpdateBy(SessionUser.getUserName());
            req.setUpdateDate(new Date());
            idList.add(req.getId());
        }
        orderReqDao.updateOrderReqBatch(list);
        //批量保存日志
        processLogService.saveProcessLogBatch(idList, reqStatus, desc, reason);
    }
}
