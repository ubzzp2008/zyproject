package com.fl.web.service.impl.store;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.qm.IMaraCensorDao;
import com.fl.web.dao.sd.IOrderDeliveryDao;
import com.fl.web.dao.store.IStoreInfoDao;
import com.fl.web.dao.store.IStoreItemDao;
import com.fl.web.dao.store.IStoreOrderDao;
import com.fl.web.entity.store.TStoreInfo;
import com.fl.web.entity.store.TStoreItem;
import com.fl.web.entity.store.TStoreOrder;
import com.fl.web.model.store.StoreOrder;
import com.fl.web.service.mdm.IBaseSerialNoService;
import com.fl.web.service.store.IStoreOrderService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.StringUtil;
import com.fl.web.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：StoreOrderServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:39
 */
@Service
@Transactional
public class StoreOrderServiceImpl implements IStoreOrderService {
    @Autowired
    private IStoreOrderDao storeOrderDao;
    @Autowired
    private IStoreItemDao storeItemDao;
    @Autowired
    private IBaseSerialNoService baseSerialNoService;
    @Autowired
    private IOrderDeliveryDao orderDeliveryDao;
    @Autowired
    private IMaraCensorDao maraCensorDao;
    @Autowired
    private IStoreInfoDao storeInfoDao;

    @Override
    public PageInfo<TStoreOrder> queryStoreOrderList(StoreOrder info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TStoreOrder> list = storeOrderDao.queryStoreOrderList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public String addStoreOrder(TStoreOrder info, String censorFlag) {
        String storeNo = baseSerialNoService.getSerialNoByType(StaticParam.WIN, StaticParam.SN_3);
        //完善申请单头信息
        info.setId(UUIDUtil.get32UUID());
        info.setStoreNo(storeNo);
        info.setOptType(StaticParam.IN);
        info.setStatus(StaticParam.ADD);
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        //完善申请单行信息
        List<TStoreItem> itemList = new ArrayList<>();
        List<String> mdIdList = new ArrayList<>();
        int count = 1;
        for (TStoreItem item : info.getItemList()) {
            item.setId(UUIDUtil.get32UUID());
            item.setStoreNo(storeNo);
            item.setItemNo(count * 10);
            item.setStatus(StaticParam.ADD);
            item.setCreateBy(SessionUser.getUserName());
            item.setCreateDate(new Date());
            itemList.add(item);
            if (StringUtil.equals("0", censorFlag)) {
                mdIdList.add(item.getDeliveryId());//需要更新orderDelivery表或者maraCensor表的id集合
            }
            if (StringUtil.equals("1", censorFlag)) {
                mdIdList.add(item.getCensorId());//需要更新orderDelivery表或者maraCensor表的id集合
            }
            count++;
        }
        storeOrderDao.saveStoreOrder(info);
        storeItemDao.saveStoreItemBatch(itemList);
        //更新orderDelivery表状态或者更新maraCensor表状态
        Map<String, Object> map = new HashMap<>();
        map.put("idList", mdIdList);
        map.put("status", StaticParam.MOD);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        if (StringUtil.equals(censorFlag, "0")) {//免检
            map.put("state", StaticParam.DELIV_50);//已入库
            orderDeliveryDao.updateDeliveryStatusBatch(map);
        }
        if (StringUtil.equals(censorFlag, "1")) {//质检
            map.put("state", StaticParam.SJ_60);//已入库
            maraCensorDao.updateMaraCensor(map);
        }
        //保存并更新库存信息
        for (TStoreItem item : info.getItemList()) {
            //构建库存数据
            TStoreInfo storeInfo = storeInfoDao.getStoreInfoByMatnr(item.getMatnr(), item.getWareCode(), item.getWarePosCode());
            if (storeInfo != null) {
                storeInfo.setStoreNum(item.getOptNum());
                storeInfo.setUsableNum(item.getOptNum());
                storeInfo.setStatus(StaticParam.MOD);
                storeInfo.setUpdateBy(SessionUser.getUserName());
                storeInfo.setUpdateDate(new Date());
                storeInfoDao.updateStoreInfo(storeInfo);
            } else {
                storeInfo = new TStoreInfo();
                storeInfo.setId(UUIDUtil.get32UUID());
                storeInfo.setMatnr(item.getMatnr());
                storeInfo.setWareCode(item.getWareCode());
                storeInfo.setWarePosCode(item.getWarePosCode());
                storeInfo.setStoreNum(item.getOptNum());
                storeInfo.setFrozenNum(new BigDecimal(0));
                storeInfo.setUsableNum(item.getOptNum());
                storeInfo.setStatus(StaticParam.ADD);
                storeInfo.setCreateBy(SessionUser.getUserName());
                storeInfo.setCreateDate(new Date());
                storeInfoDao.saveStoreInfo(storeInfo);
            }
        }
        return storeNo;
    }

    @Override
    public String saveOutStoreOrder(TStoreOrder info) {
        String storeNo = baseSerialNoService.getSerialNoByType(StaticParam.WOUT, StaticParam.SN_3);
        //完善申请单头信息
        info.setId(UUIDUtil.get32UUID());
        info.setStoreNo(storeNo);
        info.setOptType(StaticParam.OUT);
        info.setStatus(StaticParam.ADD);
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        //完善申请单行信息
        List<TStoreItem> itemList = new ArrayList<>();
        List<TStoreInfo> storeInfoList = new ArrayList<>();
        int count = 1;
        for (TStoreItem item : info.getItemList()) {
            item.setId(UUIDUtil.get32UUID());
            item.setStoreNo(storeNo);
            item.setItemNo(count * 10);
            item.setStatus(StaticParam.ADD);
            item.setCreateBy(SessionUser.getUserName());
            item.setCreateDate(new Date());
            itemList.add(item);

            //构建库存数据
            TStoreInfo storeInfo = new TStoreInfo();
            storeInfo.setMatnr(item.getMatnr());
            storeInfo.setWareCode(item.getWareCode());
            storeInfo.setWarePosCode(item.getWarePosCode());
            storeInfo.setFrozenNum(item.getOptNum());//冻结库存增加
            storeInfo.setUsableNum(item.getOptNum().multiply(new BigDecimal(-1)));//可用数减少
            storeInfo.setStatus(StaticParam.MOD);
            storeInfo.setUpdateBy(SessionUser.getUserName());
            storeInfo.setUpdateDate(new Date());
            storeInfoList.add(storeInfo);
            count++;
        }
        storeOrderDao.saveStoreOrder(info);
        storeItemDao.saveStoreItemBatch(itemList);
        //需要更新库存中的冻结数量
        if (CollectionUtils.isNotEmpty(storeInfoList)) {
            storeInfoDao.updateStoreInfoBatch(storeInfoList);
        }
        return storeNo;
    }

    @Override
    public Integer findStoreOrderByWarePosCode(String warePosCode) {
        return storeOrderDao.findStoreOrderByWarePosCode(warePosCode);
    }

    @Override
    public Integer checkStoreOrderStatus(String id, String state) {
        return storeOrderDao.checkStoreOrderStatus(id, state);
    }

    @Override
    public Integer checkStoreOrderStatusBatch(List<String> idList, String state) {
        return storeOrderDao.checkStoreOrderStatusBatch(idList, state);
    }

    @Override
    public void updateStoreOrderStatusBatch(List<String> idList, String state) {
        Map<String, Object> param = new HashMap<>();
        param.put("idList", idList);
        param.put("state", state);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        param.put("status", StaticParam.MOD);
        storeOrderDao.updateStoreOrderStatusBatch(param);
    }

    @Override
    public TStoreOrder getStoreOrderByStoreNo(String storeNo) {
        TStoreOrder order = storeOrderDao.getStoreOrderByStoreNo(storeNo);
        if (order != null) {
            List<TStoreItem> itemList = storeItemDao.getStoreItemByStoreNo(storeNo);
            order.setItemList(itemList);
        }
        return order;
    }
}
