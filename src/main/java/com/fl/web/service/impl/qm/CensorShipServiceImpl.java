package com.fl.web.service.impl.qm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.qm.ICensorShipHeadDao;
import com.fl.web.dao.qm.ICensorShipItemDao;
import com.fl.web.entity.qm.TCensorShipHead;
import com.fl.web.entity.qm.TCensorShipItem;
import com.fl.web.model.qm.CensorShipHead;
import com.fl.web.service.qm.ICensorShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：CensorShipItemServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-10 15:30
 */
@Service
@Transactional
public class CensorShipServiceImpl implements ICensorShipService {
    @Autowired
    private ICensorShipHeadDao censorShipHeadDao;
    @Autowired
    private ICensorShipItemDao censorShipItemDao;

    @Override
    public PageInfo<TCensorShipHead> queryCensorShipList(CensorShipHead info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TCensorShipHead> list = censorShipHeadDao.queryCensorShipList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public TCensorShipHead getCensorShipDetail(String censorNo) {
        TCensorShipHead head = censorShipHeadDao.getCensorShipHeadByNo(censorNo);
        if (head != null) {
            List<TCensorShipItem> itemList = censorShipItemDao.getCensorShipItemByNo(censorNo);
            head.setItemList(itemList);
        }
        return head;
    }
}
