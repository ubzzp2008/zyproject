package com.fl.web.dao.store;

import com.fl.web.entity.store.TStoreItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IStoreItemDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:40
 */
public interface IStoreItemDao {
    /**
     * @description：批量保存行项目
     * @author：justin
     * @date：2020-02-13 14:37
     */
    public void saveStoreItemBatch(List<TStoreItem> itemList);

    /**
     * @description：根据单号获取行项目
     * @author：justin
     * @date：2020-02-21 10:17
     */
    public List<TStoreItem> getStoreItemByStoreNo(@Param("storeNo") String storeNo);
}
