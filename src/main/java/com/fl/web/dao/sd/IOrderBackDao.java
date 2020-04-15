package com.fl.web.dao.sd;

import com.fl.web.entity.sd.TOrderBack;
import com.fl.web.model.sd.OrderBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrderBackDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-14 14:25
 */
public interface IOrderBackDao {
    /**
     * @description：批量保存
     * @author：justin
     * @date：2020-01-14 14:28
     */
    public void saveOrderBackBatch(List<TOrderBack> list);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-01-14 16:22
     */
    public List<TOrderBack> queryOrderBackList(OrderBack info);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-15 11:40
     */
    public Integer checkOrderBackState(@Param("id") String id, @Param("state") String state);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-15 11:40
     */
    public Integer checkOrderBackStateBatch(@Param("idList") List<String> idList, @Param("state") String state);

    /**
     * @description：更新数据
     * @author：justin
     * @date：2020-01-15 11:40
     */
    public void updateOrderBackState(Map<String, Object> param);
}
