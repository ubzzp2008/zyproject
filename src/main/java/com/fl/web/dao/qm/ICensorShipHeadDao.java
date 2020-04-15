package com.fl.web.dao.qm;

import com.fl.web.entity.qm.TCensorShipHead;
import com.fl.web.model.qm.CensorShipHead;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：ICensorShipHeadDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-10 15:22
 */
public interface ICensorShipHeadDao {
    /**
     * @description：批量保存头信息
     * @author：justin
     * @date：2020-01-10 15:27
     */
    public void saveCensorHeadList(List<TCensorShipHead> headList);

    /**
     * @description：分页查询质检单
     * @author：justin
     * @date：2020-01-13 13:57
     */
    public List<TCensorShipHead> queryCensorShipList(CensorShipHead info);

    /**
     * @description：根据质检单号获取数据
     * @author：justin
     * @date：2020-01-13 14:15
     */
    public TCensorShipHead getCensorShipHeadByNo(String censorNo);
}
