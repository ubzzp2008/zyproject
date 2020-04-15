package com.fl.web.dao.qm;

import com.fl.web.entity.qm.TCensorShipItem;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：ICensorShipItemDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-10 15:23
 */
public interface ICensorShipItemDao {

    /**
     * @description：批量保存
     * @author：justin
     * @date：2020-01-10 15:28
     */
    public void saveCensorItemList(List<TCensorShipItem> itemList);

    /**
     * @description：根据质检单号获取数据
     * @author：justin
     * @date：2020-01-13 14:19
     */
    public List<TCensorShipItem> getCensorShipItemByNo(String censorNo);
}
