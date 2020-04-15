package com.fl.web.service.qm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.qm.TCensorShipHead;
import com.fl.web.model.qm.CensorShipHead;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：ICensorShipService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-10 15:32
 */
public interface ICensorShipService {
    /**
     * @description：分页查询质检单
     * @author：justin
     * @date：2020-01-13 13:56
     */
    public PageInfo<TCensorShipHead> queryCensorShipList(CensorShipHead info);

    /**
     * @description：根据质检单号获取详情
     * @author：justin
     * @date：2020-01-13 14:12
     */
    public TCensorShipHead getCensorShipDetail(String censorNo);
}
