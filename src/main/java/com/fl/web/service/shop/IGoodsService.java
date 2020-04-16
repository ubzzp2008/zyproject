package com.fl.web.service.shop;

import com.fl.web.entity.shop.TGoods;
import com.fl.web.model.shop.Goods;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：IGoodsService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-16 10:56
 */
public interface IGoodsService {
    PageInfo<TGoods> queryGoodsList(Goods goods);

    void saveGoods(TGoods goods);

    void updateGoods(TGoods goods);

    void deleteGoods(String id);

    TGoods checkCodeExist(String code);

    TGoods getGoodsById(String id);

    List<TGoods> getAllGoodsList();
}
