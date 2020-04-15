package com.fl.web.service.impl.saleServer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.IAttachInfoDao;
import com.fl.web.dao.saleServer.IRepairMaraDao;
import com.fl.web.dao.saleServer.IRepairOrderHeadDao;
import com.fl.web.dao.saleServer.IRepairOrderItemDao;
import com.fl.web.entity.mdm.TAttachInfo;
import com.fl.web.entity.saleServer.TRepairMara;
import com.fl.web.entity.saleServer.TRepairOrderHead;
import com.fl.web.entity.saleServer.TRepairOrderItem;
import com.fl.web.entity.saleServer.TRepairOrderLog;
import com.fl.web.model.saleServer.RepairOrderHead;
import com.fl.web.model.saleServer.RepairOrderItem;
import com.fl.web.service.mdm.IBaseSerialNoService;
import com.fl.web.service.saleServer.IRepairOrderLogService;
import com.fl.web.service.saleServer.IRepairOrderService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DeviceRepairServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:25
 */
@Service
@Transactional
public class RepairOrderServiceImpl implements IRepairOrderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IRepairOrderHeadDao repairOrderHeadDao;
    @Autowired
    private IRepairOrderItemDao repairOrderItemDao;
    @Autowired
    private IBaseSerialNoService baseSerialNoService;
    @Autowired
    private IRepairMaraDao repairMaraDao;
    @Autowired
    private IAttachInfoDao attachInfoDao;
    @Autowired
    private IRepairOrderLogService repairOrderLogService;


    @Override
    public String saveRepairOrder(TRepairOrderHead info, List<TAttachInfo> list) {
        //构建头项目
        String orderNo = baseSerialNoService.getSerialNoByType(StaticParam.SN_W, StaticParam.SN_3);//获取维修单单号
        String id = UUIDUtil.get32UUID();
        info.setId(id);
        info.setOrderNo(orderNo);
        info.setOrderStatus(StaticParam.ROS_0);
        info.setStatus(StaticParam.ADD);
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        repairOrderHeadDao.saveRepairOrderHead(info);
        //保存附件
        if (CollectionUtils.isNotEmpty(list)) {
            for (TAttachInfo ta : list) {
                ta.setTableId(id);
                ta.setTableName("repair_order_head");
                ta.setOrderNo(orderNo);
                ta.setCreateBy(SessionUser.getUserName());
                ta.setCreateDate(new Date());
            }
            attachInfoDao.saveAttachInfoBatch(list);
        }
        //保存审批日志
        repairOrderLogService.saveRepairOrderLog(id, "新增报修单", "新增报修单");
        return orderNo;
    }

    @Override
    public PageInfo<TRepairOrderHead> queryRepairOrderList(RepairOrderHead info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TRepairOrderHead> list = repairOrderHeadDao.queryRepairOrderList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public PageInfo<TRepairOrderItem> queryRepairItemList(RepairOrderItem info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TRepairOrderItem> list = repairOrderItemDao.queryRepairItemList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void updateRepairOrderStatusBatch(List<String> idList, String orderStatus, String applyNote, String reason) {
        Map<String, Object> map = new HashMap<>();
        map.put("idList", idList);
        map.put("orderStatus", orderStatus);
        map.put("status", StaticParam.MOD);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        repairOrderHeadDao.updateRepairOrderStatusBatch(map);
        // 保存审批日志
        repairOrderLogService.saveRepairOrderLogBatch(idList, applyNote, reason);
    }

    @Override
    public void assignRepairOrder(List<String> idList, String userName, String realName, String reason) {
        Map<String, Object> map = new HashMap<>();
        map.put("idList", idList);
        map.put("receiveBy", userName);
        map.put("orderStatus", StaticParam.ROS_10);
        map.put("status", StaticParam.MOD);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        repairOrderHeadDao.updateRepairOrderStatusBatch(map);
        // 保存审批日志
        repairOrderLogService.saveRepairOrderLogBatch(idList, "审批通过并指派给" + realName, reason);
    }

    @Override
    public void cancelRepairOrder(String orderId, String reason) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderId);
        map.put("orderStatus", StaticParam.ROS_99);
        map.put("status", StaticParam.MOD);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        repairOrderHeadDao.updateRepairOrderStatusById(map);
        // 保存审批日志
        repairOrderLogService.saveRepairOrderLog(orderId, SessionUser.getRealName() + "作废报修单", reason);
    }

    @Override
    public void receiveRepairOrder(List<String> idList) {
        Map<String, Object> map = new HashMap<>();
        map.put("idList", idList);
        map.put("receiveBy", SessionUser.getUserName());
        map.put("orderStatus", StaticParam.ROS_10);
        map.put("status", StaticParam.MOD);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        repairOrderHeadDao.updateRepairOrderStatusBatch(map);
        // 保存审批日志
        repairOrderLogService.saveRepairOrderLogBatch(idList, "领取报修单", "手动领取报修单");
    }

    //    @Override
//    public void updateRepairOrderHeadBatch(Map<String, Object> map) {
//        map.put("status", StaticParam.MOD);
//        map.put("updateBy", SessionUser.getUserName());
//        map.put("updateDate", new Date());
//        repairOrderHeadDao.updateRepairOrderHeadBatch(map);
//        // 保存审批日志
//        List<String> idList = (List<String>) map.get("idList");
//        repairOrderLogService.saveRepairOrderLogBatch(idList, (String) map.get("reason"));
//    }

    @Override
    public void toBackRepairOrder(String id, String orderNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("orderStatus", StaticParam.ROS_0);//更改为待确认
        repairOrderHeadDao.toBackRepairOrder(map);
        //删除填写的维修方案
        repairOrderItemDao.deleteOrderItemByOrderNo(orderNo);
        // 保存审批日志
        repairOrderLogService.saveRepairOrderLog(id, SessionUser.getRealName() + "退回报修单至待确认", SessionUser.getRealName() + "退回报修单至待确认");
    }

    @Override
    public List<TRepairOrderHead> findDataByIdAndStatus(List<String> idList, String orderStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("idList", idList);
        map.put("orderStatus", orderStatus);
        return repairOrderHeadDao.findDataByIdAndStatus(map);
    }

    @Override
    public TRepairOrderHead getRepairOrderByOrderNo(String orderNo) {
        TRepairOrderHead head = repairOrderHeadDao.getOrderHeadByOrderNo(orderNo);
        if (head != null) {
            //获取行项目信息
            List<TRepairOrderItem> itemList = repairOrderItemDao.getOrderItemByOrderNo(orderNo);
            head.setItemList(itemList);
            //获取附件信息
            List<TAttachInfo> attachInfoList = attachInfoDao.getAttachInfoByOrderNo(orderNo);
            head.setAttachInfoList(attachInfoList);
            //获取流程日志信息
            List<TRepairOrderLog> logList = repairOrderLogService.getOrderLogListByOrderNo(orderNo);
            head.setLogList(logList);
        }
        return head;
    }

    @Override
    public void saveRepairOrderItem(String orderId, String orderNo, List<TRepairOrderItem> itemList) {
        //存在维修方案退回的情况，所以在保存时，需要先删除行项目在新增
        repairOrderItemDao.deleteOrderItemByOrderNo(orderNo);

        int count = 1;
        for (TRepairOrderItem item : itemList) {
            item.setId(UUIDUtil.get32UUID());
            item.setOrderNo(orderNo);
            item.setItemNo(count * 10);
            item.setStatus(StaticParam.ADD);
            item.setCreateBy(SessionUser.getUserName());
            item.setCreateDate(new Date());
            item.setDealState(StaticParam.RIS00);//待审批
            count++;//计数器+1
        }
        //保存行项目信息
        repairOrderItemDao.saveRepairOrderItem(itemList);

        Map<String, Object> param = new HashMap<>();
        param.put("id", orderId);
        param.put("orderStatus", StaticParam.ROS_15);//维修方案待审批
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        //更新单据头信息状态
        repairOrderHeadDao.updateRepairOrderStatusById(param);
        //保存审批日志
        repairOrderLogService.saveRepairOrderLog(orderId, "填写维修方案", "填写维修方案");
    }

    @Override
    public void saveApplyRepairPlan(List<String> idList, String reason) {
        List<TRepairMara> reMaraList = new ArrayList<>();
        List<TRepairOrderItem> itemArr = new ArrayList<>();
        //循环单据
        for (String orderId : idList) {
            //根据单号获取行项目
            TRepairOrderHead order = repairOrderHeadDao.getOrderHeadById(orderId);
            List<TRepairOrderItem> itemList = repairOrderItemDao.getOrderItemByOrderNo(order.getOrderNo());
            if (CollectionUtils.isNotEmpty(itemList)) {
                for (TRepairOrderItem item : itemList) {
                    switch (item.getDealType()) {
                        case StaticParam.DT01://现场维修--- DT01
                        case StaticParam.DT02://移除报废--- DT02
                        case StaticParam.DT07://维护保养--- DT07
                            item.setDealState(StaticParam.RIS20);//已完成
                            break;
                        case StaticParam.DT03://移除退坏--- DT03
                            item.setDealState(StaticParam.RIS05);//退坏中
                            //生成待邮寄数据
                            TRepairMara rm03 = initReMara(item, order.getReceiveBy());
                            rm03.setMaraFlag(StaticParam.MF_OLD);//退坏流程
                            rm03.setState(StaticParam.BJS05);//待邮寄
                            reMaraList.add(rm03);
                            break;
                        case StaticParam.DT04://新增部件--- DT04
                        case StaticParam.DT05://报废换新--- DT05
                            item.setDealState(StaticParam.RIS10);//换新中
                            TRepairMara rm04 = initReMara(item, order.getReceiveBy());
                            rm04.setMaraFlag(StaticParam.MF_NEW);//换新流程
                            // rm04.setState(StaticParam.BJS10);//待部门领导审批
                            rm04.setState(StaticParam.BJS20);//待发货确认
                            reMaraList.add(rm04);
                            break;
                        case StaticParam.DT06://退坏换新--- DT06
                            item.setDealState(StaticParam.RIS10);//换新中
                            TRepairMara rm060 = initReMara(item, order.getReceiveBy());
                            rm060.setMaraFlag(StaticParam.MF_OLD);//退坏流程
                            rm060.setState(StaticParam.BJS05);//待邮寄
                            reMaraList.add(rm060);
                            TRepairMara rm061 = initReMara(item, order.getReceiveBy());
                            rm061.setMaraFlag(StaticParam.MF_NEW);//换新流程
                            //rm061.setState(StaticParam.BJS10);//待部门领导审批
                            rm061.setState(StaticParam.BJS20);//待发货确认
                            reMaraList.add(rm061);
                            break;
                        default:
                            logger.info("数据异常！维修部件类型未定义！");
                            break;
                    }
                }
                itemArr.addAll(itemList);//行项目信息添加到外层集合中
            }
        }

        //reMaraList不为空，则表示有需要走退坏或者换新流程的单据
        if (CollectionUtils.isNotEmpty(reMaraList)) {
            //构建退坏或换新流程的单据信息
            repairMaraDao.saveRepairMaraBatch(reMaraList);
        }
        //更新行项目状态
        repairOrderItemDao.updateOrderItemStatusBatch(itemArr);

        Map<String, Object> param = new HashMap<>();
        param.put("idList", idList);
        param.put("orderStatus", StaticParam.ROS_20);//维修中
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        //更新单据头信息状态
        repairOrderHeadDao.updateRepairOrderStatusBatch(param);
        //批量保存审批日志
        repairOrderLogService.saveRepairOrderLogBatch(idList, "维修方案审批通过", reason);
    }

    @Override
    public Integer checkItemStatusByOrderId(String orderId) {
        return repairOrderItemDao.checkItemStatusByOrderId(orderId, StaticParam.RIS20);
    }

    @Override
    public void updateRepairOrderFinish(String orderId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", orderId);
        param.put("orderStatus", StaticParam.ROS_25);//待部门领导确认
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        repairOrderHeadDao.updateRepairOrderStatusById(param);
        //保存日志
        repairOrderLogService.saveRepairOrderLog(orderId, "提交维修单", "提交维修单");
    }

    /**
     * @description：构建维修物料对象
     * @author：justin
     * @date：2019-10-30 15:09
     */
    private TRepairMara initReMara(TRepairOrderItem item, String repairBy) {
        TRepairMara rm = new TRepairMara();
        rm.setId(UUIDUtil.get32UUID());
        rm.setOrderNo(item.getOrderNo());
        rm.setItemId(item.getId());
        rm.setItemNo(item.getItemNo());
        rm.setMatnr(item.getMatnr());
        rm.setMaktx(item.getMaktx());
        rm.setNorms(item.getNorms());
        rm.setUnit(item.getUnit());
        rm.setNum(item.getNum());
        rm.setRepairBy(repairBy);
        rm.setCreateBy(SessionUser.getUserName());
        rm.setCreateDate(new Date());
        rm.setStatus(StaticParam.ADD);
        return rm;
    }

    @Override
    public List<TRepairOrderHead> exportRepairOrder(RepairOrderHead info) {
        return repairOrderHeadDao.queryRepairOrderList(info);
    }

    @Override
    public PageInfo<TRepairOrderHead> repairOrderByKunnrList(RepairOrderHead info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TRepairOrderHead> list = repairOrderHeadDao.repairOrderByKunnrList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public PageInfo<TRepairOrderItem> repairOrderDetailList(RepairOrderItem info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TRepairOrderItem> list = repairOrderItemDao.repairOrderDetailList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }


}
