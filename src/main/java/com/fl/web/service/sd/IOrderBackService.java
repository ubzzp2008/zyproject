package com.fl.web.service.sd;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.sd.TOrderBack;
import com.fl.web.model.sd.OrderBack;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderBackService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-14 14:25
 */
public interface IOrderBackService {
    /**
     * @description：批量保存
     * @author：justin
     * @date：2020-01-14 14:28
     */
    public void saveOrderBackBatch(List<TOrderBack> list);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-01-14 16:21
     */
    public PageInfo<TOrderBack> queryOrderBackList(OrderBack info);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-15 11:33
     */
    public Integer checkOrderBackState(String id, String state);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-15 11:33
     */
    public Integer checkOrderBackStateBatch(List<String> idList, String state);

    /**
     * @description：报废处理
     * @author：justin
     * @date：2020-01-15 11:32
     */
    public void scrapOrderMara(List<TOrderBack> infoList);

    /**
     * @description：换货处理
     * @author：justin
     * @date：2020-01-15 11:33
     */
    public void exchangeOrderMara(List<TOrderBack> infoList);

    /**
     * @description：退货处理
     * @author：justin
     * @date：2020-01-15 11:33
     */
    public void backOrderMara(List<TOrderBack> infoList);
}
