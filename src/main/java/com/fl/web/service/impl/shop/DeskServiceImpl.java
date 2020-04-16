package com.fl.web.service.impl.shop;

import com.fl.web.dao.shop.IDeskDao;
import com.fl.web.entity.shop.TDesk;
import com.fl.web.model.shop.Desk;
import com.fl.web.service.shop.IDeskService;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：GoodsServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-16 10:56
 */
@Service
@Transactional
public class DeskServiceImpl implements IDeskService {
    @Autowired
    private IDeskDao deskDao;

    @Override
    public PageInfo<TDesk> queryDeskList(Desk desk) {
        PageHelper.startPage(desk.getPageNum(), desk.getPageSize());
        List<TDesk> userList = deskDao.queryDeskList(desk);
        PageInfo result = new PageInfo(userList);
        return result;
    }

    @Override
    public void saveDesk(TDesk desk) {
        desk.setId(UUIDUtil.get32UUID());
        desk.setStatus(StaticParam.ADD);
        desk.setCreateDate(new Date());
        deskDao.saveDesk(desk);
    }

    @Override
    public void updateDesk(TDesk desk) {
        desk.setStatus(StaticParam.MOD);
        desk.setUpdateDate(new Date());
        deskDao.updateDesk(desk);
    }

    @Override
    public void deleteDesk(String id) {
        deskDao.deleteDesk(id);
    }

    @Override
    public TDesk checkCodeExist(String code) {
        return deskDao.checkCodeExist(code);
    }

    @Override
    public TDesk getDeskById(String id) {
        return deskDao.getDeskById(id);
    }

    @Override
    public List<TDesk> getAllDeskList() {
        return deskDao.queryDeskList(new Desk());
    }
}
