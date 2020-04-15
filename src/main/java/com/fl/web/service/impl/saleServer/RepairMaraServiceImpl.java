package com.fl.web.service.impl.saleServer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.saleServer.IRepairMaraDao;
import com.fl.web.dao.saleServer.IRepairOrderItemDao;
import com.fl.web.entity.saleServer.TRepairMara;
import com.fl.web.model.saleServer.RepairMara;
import com.fl.web.service.saleServer.IRepairMaraService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RepairMaraServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-30 14:59
 */
@Service
@Transactional
public class RepairMaraServiceImpl implements IRepairMaraService {
    @Autowired
    private IRepairMaraDao repairMaraDao;
    @Autowired
    private IRepairOrderItemDao repairOrderItemDao;

    @Override
    public PageInfo<TRepairMara> queryRepairMaraList(RepairMara info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TRepairMara> list = repairMaraDao.queryRepairMaraList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void updateRepairMara(Map<String, Object> param) {
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        repairMaraDao.updateRepairMara(param);
    }

    @Override
    public void saveReceiveMara(List<TRepairMara> maraList) {
        Map<String, Object> param = new HashMap<>();
        Map<String, String> itemIdMap = new HashMap<>();
        List<String> idList = new ArrayList<>();
        for (TRepairMara mara : maraList) {
            idList.add(mara.getId());
            itemIdMap.put(mara.getItemId(), mara.getItemId());
        }
        param.put("idList", idList);
        param.put("state", StaticParam.BJS50);
        param.put("status", StaticParam.MOD);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        repairMaraDao.updateRepairMara(param);

        //确认收货后，需要更新维修单单据状态
        for (String itemId : itemIdMap.keySet()) {
            //根据单据行ID检查该部件是否流程已经结束
            Integer isEnd = repairMaraDao.findRepairMaraByItemId(itemId, StaticParam.BJS50);
            if (isEnd == 0) {//不存在未结束的，则更新单据行项目信息
                Map<String, Object> map = new HashMap<>();
                map.put("id", itemId);
                map.put("dealState", StaticParam.RIS20);
                map.put("status", StaticParam.MOD);
                map.put("updateBy", SessionUser.getUserName());
                map.put("updateDate", new Date());
                repairOrderItemDao.updateRepairOrderItemById(map);
            }
        }
    }

    @Override
    public List<TRepairMara> getRepairMaraByOrderNoList(List<String> orderNoList) {
        return repairMaraDao.getRepairMaraByOrderNoList(orderNoList);
    }
}
