package com.fl.web.service.impl.sd;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.sd.IOrderBackDao;
import com.fl.web.dao.sd.IOrderHeadDao;
import com.fl.web.dao.sd.IOrderItemDao;
import com.fl.web.entity.sd.TOrderBack;
import com.fl.web.entity.sd.TOrderItem;
import com.fl.web.model.sd.OrderBack;
import com.fl.web.service.sd.IOrderBackService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderBackServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-14 14:24
 */
@Service
@Transactional
public class OrderBackServiceImpl implements IOrderBackService {
    @Autowired
    private IOrderBackDao orderBackDao;
    @Autowired
    private IOrderHeadDao orderHeadDao;
    @Autowired
    private IOrderItemDao orderItemDao;

    @Override
    public PageInfo<TOrderBack> queryOrderBackList(OrderBack info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TOrderBack> list = orderBackDao.queryOrderBackList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public Integer checkOrderBackState(String id, String state) {
        return orderBackDao.checkOrderBackState(id, state);
    }

    @Override
    public Integer checkOrderBackStateBatch(List<String> idList, String state) {
        return orderBackDao.checkOrderBackStateBatch(idList, state);
    }

    @Override
    public void scrapOrderMara(List<TOrderBack> infoList) {
        Map<String, Object> param = new HashMap<>();
        List<String> idList = new ArrayList<>();
        for (TOrderBack back : infoList) {
            idList.add(back.getId());
        }
        param.put("idList", idList);
        param.put("state", StaticParam.MB_99);//报废
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        orderBackDao.updateOrderBackState(param);
    }

    @Override
    public void exchangeOrderMara(List<TOrderBack> infoList) {
        Map<String, Object> param = new HashMap<>();
        List<TOrderItem> itemList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for (TOrderBack back : infoList) {
            idList.add(back.getId());
            TOrderItem item = new TOrderItem();
            item.setId(back.getItemId());
            item.setNum(back.getBackNum());
            item.setStatus(StaticParam.MOD);
            item.setUpdateBy(SessionUser.getUserName());
            item.setUpdateDate(new Date());
            itemList.add(item);
        }
        param.put("idList", idList);
        param.put("state", StaticParam.MB_30);//换货处理
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        orderBackDao.updateOrderBackState(param);
        //todo 换货时，将已收货数据退回，更新为待收货
        //todo 待实现 退回订单行的已收货数量
        //todo 待实现 更新订单头状态

    }

    @Override
    public void backOrderMara(List<TOrderBack> infoList) {
        Map<String, Object> param = new HashMap<>();
        List<String> idList = new ArrayList<>();
        for (TOrderBack back : infoList) {
            idList.add(back.getId());
        }
        param.put("idList", idList);
        param.put("state", StaticParam.MB_50);//退货处理
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        orderBackDao.updateOrderBackState(param);
    }

    @Override
    public void saveOrderBackBatch(List<TOrderBack> list) {
        orderBackDao.saveOrderBackBatch(list);
    }
}
