package com.fl.web.service.impl.shop;

import com.fl.web.dao.shop.IGoodsDao;
import com.fl.web.entity.shop.TGoods;
import com.fl.web.model.shop.Goods;
import com.fl.web.service.shop.IGoodsService;
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
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public PageInfo<TGoods> queryGoodsList(Goods goods) {
        PageHelper.startPage(goods.getPageNum(), goods.getPageSize());
        List<TGoods> userList = goodsDao.queryGoodsList(goods);
        PageInfo result = new PageInfo(userList);
        return result;
    }

    @Override
    public void saveGoods(TGoods goods) {
        goods.setId(UUIDUtil.get32UUID());
        goods.setStatus(StaticParam.ADD);
        goods.setCreateDate(new Date());
        goodsDao.saveGoods(goods);
    }

    @Override
    public void updateGoods(TGoods goods) {
        goods.setStatus(StaticParam.MOD);
        goods.setUpdateDate(new Date());
        goodsDao.updateGoods(goods);
    }

    @Override
    public void deleteGoods(String id) {
        goodsDao.deleteGoods(id);
    }

    @Override
    public TGoods checkCodeExist(String code) {
        return goodsDao.checkCodeExist(code);
    }

    @Override
    public TGoods getGoodsById(String id) {
        return goodsDao.getGoodsById(id);
    }

    @Override
    public List<TGoods> getAllGoodsList() {
        return goodsDao.queryGoodsList(new Goods());
    }
}
