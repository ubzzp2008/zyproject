package com.fl.web.service.impl.sd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.qm.IMaraCensorDao;
import com.fl.web.dao.sd.IOrderDeliveryDao;
import com.fl.web.dao.sd.IOrderHeadDao;
import com.fl.web.dao.sd.IOrderItemDao;
import com.fl.web.dao.sd.IOrderReqDao;
import com.fl.web.entity.log.TProcessLog;
import com.fl.web.entity.mdm.TMara;
import com.fl.web.entity.sd.TOrderDelivery;
import com.fl.web.entity.sd.TOrderHead;
import com.fl.web.entity.sd.TOrderItem;
import com.fl.web.model.sd.OrderHead;
import com.fl.web.model.sd.OrderItem;
import com.fl.web.service.log.IProcessLogService;
import com.fl.web.service.mdm.IBaseSerialNoService;
import com.fl.web.service.mdm.IMaraService;
import com.fl.web.service.sd.IOrderService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 17:12
 */
@Service
@Transactional
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderHeadDao orderHeadDao;
    @Autowired
    private IOrderItemDao orderItemDao;
    @Autowired
    private IBaseSerialNoService baseSerialNoService;
    @Autowired
    private IOrderReqDao orderReqDao;
    @Autowired
    private IProcessLogService processLogService;
    @Autowired
    private IMaraService maraService;
    @Autowired
    private IOrderDeliveryDao orderDeliveryDao;
    @Autowired
    private IMaraCensorDao maraCensorDao;

    @Override
    public PageInfo<TOrderHead> queryOrderList(OrderHead info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TOrderHead> list = orderHeadDao.queryOrderList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public TOrderHead getOrderByOrderNo(String orderNo) {
        TOrderHead head = orderHeadDao.getOrderHeadByOrderNo(orderNo);
        if (head != null) {
            List<TOrderItem> itemList = orderItemDao.getOrderItemByOrderNo(orderNo);
            head.setItemList(itemList);
        }
        return head;
    }

    @Override
    public TOrderHead getOrderDetailByOrderNo(String orderNo) {
        TOrderHead head = orderHeadDao.getOrderHeadByOrderNo(orderNo);
        if (head != null) {
            List<TOrderItem> itemList = orderItemDao.getOrderItemByOrderNo(orderNo);
            head.setItemList(itemList);
            List<TProcessLog> logList = processLogService.getProcessLogList(orderNo);
            head.setLogList(logList);
        }
        return head;
    }

    @Override
    public String saveOrder(TOrderHead info) {
        String orderNo = baseSerialNoService.getSerialNoByType(StaticParam.SN_PO, StaticParam.SN_3);
        //完善申请单头信息
        info.setId(UUIDUtil.get32UUID());
        info.setOrderNo(orderNo);
        info.setStatus(StaticParam.ADD);
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        //完善申请单行信息
        List<TOrderItem> itemList = new ArrayList<TOrderItem>();
        int count = 1;
        for (TOrderItem item : info.getItemList()) {
            item.setId(UUIDUtil.get32UUID());
            item.setOrderNo(orderNo);
            item.setItemNo(count * 10);
            item.setDeliveryNum(new BigDecimal(0));
            item.setUsableNum(item.getNum());
            item.setDeliveryState("-2");//待收货
            item.setStatus(StaticParam.ADD);
            item.setCreateBy(SessionUser.getUserName());
            item.setCreateDate(new Date());
            itemList.add(item);
            count++;
        }
        orderHeadDao.saveOrderHead(info);
        orderItemDao.saveOrderItemBatch(itemList);
        return orderNo;
    }

    @Override
    public String saveReqToOrder(TOrderHead info) {
        //获取可用单号
        String orderNo = baseSerialNoService.getSerialNoByType(StaticParam.SN_PO, StaticParam.SN_3);
        List<TOrderItem> itemList = new ArrayList<TOrderItem>();
        int count = 0;
        for (TOrderItem item : info.getItemList()) {
            count++;
            Map<String, Object> param = new HashMap<>();//用于回写采购订单号的参数map
            param.put("id", item.getReqId());
            param.put("orderNo", orderNo);
            param.put("reqStatus", StaticParam.REQ_50);//采购已下单
            param.put("updateBy", SessionUser.getUserName());
            param.put("updateDate", new Date());
            param.put("status", StaticParam.MOD);

            //若申请单为新物料单据，则系统自动创建物料基础信息
            if (StringUtil.equals(item.getReqFlag(), StaticParam.REQ_NEW)) {
                //根据行项目信息新建物料信息并完善申请单行信息
                TMara mara = this.initMara(item);
                String matnr = maraService.autoSaveMara(mara);
                param.put("matnr", matnr);
                this.initOrderItem(item);
                item.setOrderNo(orderNo);
                item.setItemNo(count * 10);
                item.setMatnr(matnr);
                itemList.add(item);
            } else {
                this.initOrderItem(item);
                item.setOrderNo(orderNo);
                item.setItemNo(count * 10);
                itemList.add(item);
                //回写采购单价到物料基础表中
                maraService.updateMaraPrice(item.getMatnr(), item.getPrice());
            }
            //根据reqId回写采购订单号
            orderReqDao.updateOrderNoById(param);
            //保存申请单日志
            processLogService.saveProcessLog(new TProcessLog(item.getReqId(), StaticParam.REQ_50, "采购已下单", "采购已下单"));
        }
        //完善申请单头信息
        info.setId(UUIDUtil.get32UUID());
        info.setOrderNo(orderNo);
        info.setStatus(StaticParam.ADD);
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        //保存订单信息
        orderHeadDao.saveOrderHead(info);
        orderItemDao.saveOrderItemBatch(itemList);
        if (StringUtil.equals(StaticParam.ORD_10, info.getOrderStatus())) {
            //保存订单日志
            processLogService.saveProcessLog(new TProcessLog(orderNo, info.getOrderStatus(), "提交采购订单", "提交采购订单"));
        }
        return orderNo;
    }

    private TMara initMara(TOrderItem item) {
        TMara mara = new TMara();
        mara.setMaktx(item.getMaktx());
        mara.setNorms(item.getNorms());
        mara.setUnit(item.getUnit());
        mara.setMaktl(item.getMaktl());
        mara.setQualityType(item.getQualityType());
        mara.setManufacturer(item.getManufacturer());
        mara.setPrice(item.getPrice());
        return mara;
    }

    private TOrderItem initOrderItem(TOrderItem item) {
        item.setId(UUIDUtil.get32UUID());
        item.setDeliveryNum(new BigDecimal(0));
        item.setUsableNum(item.getNum());
        item.setDeliveryState("-2");//待收货
        item.setStatus(StaticParam.ADD);
        item.setCreateBy(SessionUser.getUserName());
        item.setCreateDate(new Date());
        return item;
    }

    @Override
    public Integer checkOrderStatus(String orderNo, String orderStatus) {
        return orderHeadDao.checkOrderStatus(orderNo, orderStatus);
    }

    @Override
    public Integer checkOrderStatusBatch(List<String> orderNoList, String orderStatus) {
        return orderHeadDao.checkOrderStatusBatch(orderNoList, orderStatus);
    }


    @Override
    public void updateOrderStatusBatch(List<String> orderNoList, String orderStatus, String desc, String reason) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderNoList", orderNoList);
        param.put("orderStatus", orderStatus);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        param.put("status", StaticParam.MOD);
        orderHeadDao.updateOrderStatusBatch(param);
        //批量保存日志
        processLogService.saveProcessLogBatch(orderNoList, orderStatus, desc, reason);
    }

    @Override
    public void deleteOrder(String orderNo, String reqId) {
        Map<String, Object> param = new HashMap<>();
        param.put("orderNo", orderNo);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        param.put("status", StaticParam.DEL);
        //删除单据行信息
        orderItemDao.deleteOrderItem(param);
        //删除单据头信息
        orderHeadDao.deleteOrderHead(param);
        //更新采购需求状态
        param.put("id", reqId);
        param.put("reqStatus", StaticParam.REQ_30);
        param.put("status", StaticParam.MOD);
        orderReqDao.updateOrderReqStatus(param);
        //记录采购需求日志
        processLogService.saveProcessLog(new TProcessLog(reqId, StaticParam.REQ_30, "待采购下单", "采购作废单据待重新下单"));
    }

    @Override
    public void updateSaveOrder(TOrderHead info) {
        //完善申请单头信息
        info.setStatus(StaticParam.MOD);
        info.setUpdateBy(SessionUser.getUserName());
        info.setUpdateDate(new Date());
        //更新信息
        orderHeadDao.updateOrderHead(info);
        orderItemDao.updateOrderItemBatch(info.getItemList());
        //保存日志，提交时才保存日志，保存时不保存日志
        if (StringUtil.equals(StaticParam.ORD_10, info.getOrderStatus())) {
            processLogService.saveProcessLog(new TProcessLog(info.getOrderNo(), StaticParam.ORD_10, "提交采购订单", "提交采购订单"));
        }
    }

    @Override
    public PageInfo<TOrderItem> queryWaitDeliveryList(OrderItem info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TOrderItem> list = orderItemDao.queryWaitDeliveryList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public TOrderItem checkDeliveryNum(String id) {
        return orderItemDao.checkDeliveryNum(id);
    }

    @Override
    public void deliveryOrderItem(List<TOrderItem> infoList) {
        List<String> orderNoList = new ArrayList<>();
        List<TOrderDelivery> deliveryList = new ArrayList<>();//收货数据集合
        //完善数据
        for (TOrderItem item : infoList) {
            TOrderDelivery delivery = initOrderDelivery(item);
            delivery.setDeliveryFlag(StaticParam.DELIV_0);//正常收货
            delivery.setState(StaticParam.DELIV_0);
            deliveryList.add(delivery);
            //将单号放入集合中，后面需要修改单号的状态
            orderNoList.add(delivery.getOrderNo());
            //完善信息
            item.setUpdateBy(SessionUser.getUserName());
            item.setUpdateDate(new Date());
        }
        //保存收货信息
        orderDeliveryDao.saveOrderDeliveryBatch(deliveryList);
        //更新订单行项目的收货信息
        orderItemDao.updateItemDeliveryInfo(infoList);
        //根据行项目收货情况更新采购订单状态
        updateDeliveryStatus(orderNoList);
    }

    @Override
    public void deliveryDiffOrderItem(List<TOrderItem> infoList) {
        List<String> orderNoList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        List<TOrderDelivery> deliveryList = new ArrayList<>();//收货数据集合
        //完善数据
        for (TOrderItem item : infoList) {
            TOrderDelivery delivery = initOrderDelivery(item);
            delivery.setDeliveryFlag(StaticParam.DELIV_99);//差异收货
            delivery.setState(StaticParam.DELIV_99);
            deliveryList.add(delivery);

            idList.add(item.getId());//订单行id集合
            //将单号放入集合中，后面需要修改单号的状态
            orderNoList.add(delivery.getOrderNo());
        }
        //保存收货信息
        orderDeliveryDao.saveOrderDeliveryBatch(deliveryList);
        //更新行项目状态
        Map<String, Object> diffMap = new HashMap<>();
        diffMap.put("idList", idList);
        diffMap.put("deliveryState", StaticParam.DELIV_99);//行项目差异收货
        diffMap.put("note", infoList.get(0).getDiffNote());//行项目差异收货备注
        diffMap.put("updateBy", SessionUser.getUserName());
        diffMap.put("updateDate", new Date());
        diffMap.put("status", StaticParam.MOD);
        orderItemDao.updateItemDeliveryDiff(diffMap);
        //根据行项目收货情况更新采购订单状态
        updateDeliveryStatus(orderNoList);
    }


    /**
     * @description：构建收货信息
     * @author：justin
     * @date：2020-01-21 12:20
     */
    private TOrderDelivery initOrderDelivery(TOrderItem item) {
        TOrderDelivery delivery = new TOrderDelivery();
        delivery.setId(UUIDUtil.get32UUID());
        delivery.setOrderNo(item.getOrderNo());
        delivery.setItemId(item.getId());
        delivery.setItemNo(item.getItemNo());
        delivery.setReqId(item.getReqId());
        delivery.setMatnr(item.getMatnr());
        delivery.setMaktx(item.getMaktx());
        delivery.setNorms(item.getNorms());
        delivery.setUnit(item.getUnit());
        delivery.setMaktl(item.getMaktl());
        delivery.setOrderNum(item.getNum());
        delivery.setNum(item.getUsableNum());//本次收货数量
        delivery.setStoreNum(item.getUsableNum());//待入库数
        delivery.setQualityType(item.getQualityType());
        delivery.setManufacturer(item.getManufacturer());
        delivery.setNote(item.getDiffNote());
        delivery.setCensorFlag(item.getCensorFlag());
        delivery.setCreateBy(SessionUser.getUserName());
        delivery.setCreateDate(new Date());
        delivery.setStatus(StaticParam.ADD);
        return delivery;
    }

    /**
     * @description：更新采购订单状态
     * @author：justin
     * @date：2020-01-10 10:03
     */
    public void updateDeliveryStatus(List<String> orderNoList) {
        //根据单号检查该订单是否收货操作完成---更新订单收货状态
        List<String> list = orderNoList.stream().distinct().collect(Collectors.toList());//集合排重
        for (String orderNo : list) {
            Integer sumState = orderItemDao.checkDeliveryState(orderNo);
            String orderStatus = "";
            String reason = "";
            if (sumState.equals(0)) {
                //完全收货
                orderStatus = StaticParam.ORD_30;
                reason = "完全收货";
            } else if (sumState.equals(99)) {
                //差异收货
                orderStatus = StaticParam.ORD_22;
                reason = "差异收货";
            } else {
                //部分收货
                orderStatus = StaticParam.ORD_21;
                reason = "部分收货";
            }
            Map<String, Object> param = new HashMap<>();
            param.put("orderNo", orderNo);
            param.put("orderStatus", orderStatus);
            param.put("updateBy", SessionUser.getUserName());
            param.put("updateDate", new Date());
            param.put("status", StaticParam.MOD);
            orderHeadDao.updateOrderStatus(param);
            //保存收货日志
            processLogService.saveProcessLog(new TProcessLog(orderNo, orderStatus, reason, reason));
        }
    }
}
