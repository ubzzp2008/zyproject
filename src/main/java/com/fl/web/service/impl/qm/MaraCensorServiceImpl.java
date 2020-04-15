package com.fl.web.service.impl.qm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.qm.ICensorShipHeadDao;
import com.fl.web.dao.qm.ICensorShipItemDao;
import com.fl.web.dao.qm.IMaraCensorDao;
import com.fl.web.dao.sd.IOrderBackDao;
import com.fl.web.dao.sd.IOrderDeliveryDao;
import com.fl.web.entity.qm.TCensorShipHead;
import com.fl.web.entity.qm.TCensorShipItem;
import com.fl.web.entity.qm.TMaraCensor;
import com.fl.web.entity.sd.TOrderBack;
import com.fl.web.entity.sd.TOrderDelivery;
import com.fl.web.model.qm.MaraCensor;
import com.fl.web.service.mdm.IBaseSerialNoService;
import com.fl.web.service.qm.IMaraCensorService;
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
 * @类名称：MaraCensorServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 16:56
 */
@Service
@Transactional
public class MaraCensorServiceImpl implements IMaraCensorService {
    @Autowired
    private IMaraCensorDao maraCensorDao;
    @Autowired
    private IBaseSerialNoService baseSerialNoService;
    @Autowired
    private ICensorShipHeadDao censorShipHeadDao;
    @Autowired
    private ICensorShipItemDao censorShipItemDao;
    @Autowired
    private IOrderBackDao orderBackDao;
    @Autowired
    private IOrderDeliveryDao orderDeliveryDao;

    @Override
    public PageInfo<TMaraCensor> queryMaraCensorList(MaraCensor info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TMaraCensor> list = maraCensorDao.queryMaraCensorList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public Integer checkCensorState(String id, String state) {
        return maraCensorDao.checkCensorState(id, state);
    }

    @Override
    public Integer checkCensorStateBatch(List<String> idList, String state) {
        return maraCensorDao.checkCensorStateBatch(idList, state);
    }

    @Override
    public void qmConfirmReceiveCensor(List<String> idList, String state) {
        Map<String, Object> param = new HashMap<>();
        param.put("idList", idList);
        param.put("state", state);
        param.put("receiveUser", SessionUser.getRealName());
        param.put("receiveDate", new Date());
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        maraCensorDao.updateMaraCensor(param);
    }

    @Override
    public void saveInspectResult(List<TMaraCensor> infoList) {
        for (TMaraCensor censor : infoList) {
            censor.setCheckUser(SessionUser.getRealName());
            censor.setCheckDate(new Date());
            censor.setStatus(StaticParam.MOD);
            censor.setUpdateBy(SessionUser.getUserName());
            censor.setUpdateDate(new Date());
            //判断是否存在不合格数量，不合格数量大于0
            if (censor.getUnqualifiedNum().compareTo(new BigDecimal(0)) == 1) { //-1小于，0等于，1大于
                censor.setState(StaticParam.SJ_15);//存在不合格数量的，需要部门领导确认下
            } else {
                censor.setState(StaticParam.SJ_20);//全部合格，直接到待创建质检单
            }
        }
        maraCensorDao.saveInspectResult(infoList);
    }

    @Override
    public void updateMaraCensor(List<String> idList, String state) {
        Map<String, Object> param = new HashMap<>();
        param.put("idList", idList);
        param.put("state", state);
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        maraCensorDao.updateMaraCensor(param);
    }

    @Override
    public void saveCensorShip(List<String> idList) {
        //根据idList获取数据，并按订单号排序
        List<TMaraCensor> infoList = maraCensorDao.findMaraCensorByIdList(idList);
        if (CollectionUtils.isNotEmpty(infoList)) {
            //循环并按订单号构建质检单信息
            List<TCensorShipHead> headList = new ArrayList<>();
            List<TCensorShipItem> itemList = new ArrayList<>();
            List<TOrderBack> mbList = new ArrayList<>();//不合格数量待处理集合
            String orderNo = "";
            String sjNo = "";//送检单单号
            String batchNo = "";//批次号
            Integer count = 0;//行项目计数器
            for (TMaraCensor censor : infoList) {
                //判断是否存在不合格数量，不合格数量大于0
                if (censor.getUnqualifiedNum().compareTo(new BigDecimal(0)) == 1) { //-1小于，0等于，1大于
                    //构建TMaraBack对象集合，并保存
                    TOrderBack mb = this.initOrderBack(censor);
                    mb.setBackNum(censor.getUnqualifiedNum());//不合格数量赋值给退回数量
                    mb.setReason(censor.getReason());
                    mb.setState(StaticParam.MB_5);
                    mbList.add(mb);
                }
                //根据单号分组构建质检单信息
                if (!StringUtil.equals(orderNo, censor.getOrderNo())) {
                    orderNo = censor.getOrderNo();//重置单号
                    count = 1;//重置计数器
                    //构建头信息
                    sjNo = baseSerialNoService.getSerialNoByType(StaticParam.SN_JY, StaticParam.SN_3);//获取送检单单号
                    //获取批号，不直接获取，采用送检单号，替换前两位字母，这样保证送检单号与批号号码一致
                    batchNo = sjNo.replace(StaticParam.SN_JY, StaticParam.SN_HL);
                    TCensorShipHead head = new TCensorShipHead();
                    head.setId(UUIDUtil.get32UUID());
                    head.setOrderNo(orderNo);
                    head.setCensorNo(sjNo);
                    head.setSupplierCode(censor.getSupplierCode());
                    head.setSupplierName(censor.getSupplierName());
                    head.setPurchaser(censor.getPurchaser());
                    head.setCreateBy(SessionUser.getUserName());
                    head.setCreateDate(new Date());
                    head.setStatus(StaticParam.ADD);
                    head.setState("0");
                    head.setExportFlag("0");
                    headList.add(head);
                }
                //构建行项目信息
                TCensorShipItem item = this.initCensorItem(censor);
                item.setItemNo(count * 10);
                item.setOrderNo(orderNo);
                item.setCensorNo(sjNo);
                item.setBatchNo(batchNo);
                itemList.add(item);
                count++;//行项目计数器+1
            }
            //保存送检单头和行项目
            censorShipHeadDao.saveCensorHeadList(headList);
            censorShipItemDao.saveCensorItemList(itemList);
            //更新送检物料状态
            Map<String, Object> map = new HashMap<>();
            map.put("idList", idList);
            map.put("state", StaticParam.SJ_30);
            map.put("updateBy", SessionUser.getUserName());
            map.put("updateDate", new Date());
            map.put("status", StaticParam.MOD);
            maraCensorDao.updateMaraCensor(map);
            if (CollectionUtils.isNotEmpty(mbList)) {
                //将不合格数据给采购进行退换货处理
                orderBackDao.saveOrderBackBatch(mbList);
            }
        }
    }


    private TCensorShipItem initCensorItem(TMaraCensor censor) {
        //构建行项目信息
        TCensorShipItem item = new TCensorShipItem();
        item.setId(UUIDUtil.get32UUID());
        item.setOrderItemId(censor.getItemId());
        item.setCensorId(censor.getId());
        item.setMaktl(censor.getMaktl());
        item.setMatnr(censor.getMatnr());
        item.setMaktx(censor.getMaktx());
        item.setNorms(censor.getNorms());
        item.setUnit(censor.getUnit());
        item.setNum(censor.getNum());
        item.setUnqualifiedNum(censor.getUnqualifiedNum());
        item.setQualifiedNum(censor.getQualifiedNum());
        item.setManufacturer(censor.getManufacturer());
        item.setQualityType(censor.getQualityType());
        item.setGenerationDate(censor.getGenerationDate());
        item.setValidityDate(censor.getValidityDate());
        item.setCreateBy(SessionUser.getUserName());
        item.setCreateDate(new Date());
        item.setStatus(StaticParam.ADD);
        return item;
    }

    @Override
    public void qmRejectReceiveCensor(List<String> idList, String reason) {
        List<TOrderDelivery> deliveryList = new ArrayList<>();
        List<TMaraCensor> censorList = maraCensorDao.findMaraCensorByIdList(idList);
        //更新order_delivery数据状态
        for (TMaraCensor censor : censorList) {
            TOrderDelivery delivery = new TOrderDelivery();
            delivery.setId(censor.getDeliveryId());
            delivery.setState(StaticParam.DELIV_11);
            delivery.setBackBy(SessionUser.getRealName());
            delivery.setBackDate(new Date());
            delivery.setBackReason(reason);
            delivery.setStatus(StaticParam.MOD);
            delivery.setUpdateBy(SessionUser.getUserName());
            delivery.setUpdateDate(new Date());
            deliveryList.add(delivery);
        }
        //批量更新数据
        orderDeliveryDao.updateOrderDeliveryBatch(deliveryList);

        //删除送检数据
        Map<String, Object> param = new HashMap<>();
        param.put("idList", idList);
        param.put("status", StaticParam.DEL);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        maraCensorDao.updateMaraCensor(param);
    }

    /**
     * @description：构建TOrderBack对象数据
     * @author：justin
     * @date：2020-01-14 17:27
     */
    private TOrderBack initOrderBack(TMaraCensor censor) {
        TOrderBack mb = new TOrderBack();
        mb.setId(UUIDUtil.get32UUID());
        mb.setOrderNo(censor.getOrderNo());
        mb.setItemId(censor.getItemId());
        mb.setItemNo(censor.getItemNo());
        mb.setMaktl(censor.getMaktl());
        mb.setMatnr(censor.getMatnr());
        mb.setMaktx(censor.getMaktx());
        mb.setUnit(censor.getUnit());
        mb.setNorms(censor.getNorms());
        mb.setQualityType(censor.getQualityType());
        mb.setManufacturer(censor.getManufacturer());
        mb.setStatus(StaticParam.ADD);
        mb.setCreateBy(SessionUser.getUserName());
        mb.setCreateDate(new Date());
        return mb;
    }

    @Override
    public void comfirmToStore(List<String> idList, String state) {
        //根据idList获取数据，并按订单号排序
        List<TMaraCensor> infoList = maraCensorDao.findMaraCensorByIdList(idList);
        if (CollectionUtils.isNotEmpty(infoList)) {
            List<TOrderDelivery> deliveryList = new ArrayList<>();//需要更新收货表中的待入库数量集合
            for (TMaraCensor censor : infoList) {
                //构建需要更改待入库数量的收货数据
                TOrderDelivery delivery = new TOrderDelivery();
                delivery.setId(censor.getDeliveryId());
                delivery.setStoreNum(censor.getQualifiedNum());//将合格数量赋值给待入库数
                delivery.setState(StaticParam.DELIV_30);//质检待入库
                delivery.setStatus(StaticParam.MOD);
                delivery.setUpdateBy(SessionUser.getUserName());
                delivery.setUpdateDate(new Date());
                deliveryList.add(delivery);
            }
            if (CollectionUtils.isNotEmpty(deliveryList)) {
                //根据质检的结果更新待入库数量
                orderDeliveryDao.updateOrderDeliveryBatch(deliveryList);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("idList", idList);
            param.put("state", state);
            param.put("status", StaticParam.MOD);
            param.put("updateBy", SessionUser.getUserName());
            param.put("updateDate", new Date());
            maraCensorDao.updateMaraCensor(param);
        }
    }
}
