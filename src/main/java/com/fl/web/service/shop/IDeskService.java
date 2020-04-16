package com.fl.web.service.shop;

import com.fl.web.entity.shop.TDesk;
import com.fl.web.model.shop.Desk;
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
public interface IDeskService {
    PageInfo<TDesk> queryDeskList(Desk desk);

    void saveDesk(TDesk desk);

    void updateDesk(TDesk desk);

    void deleteDesk(String id);

    TDesk checkCodeExist(String code);

    TDesk getDeskById(String id);

    List<TDesk> getAllDeskList();
}
