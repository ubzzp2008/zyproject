package com.fl.web.dao.qm;

import com.fl.web.entity.qm.TMaraCensor;
import com.fl.web.model.qm.MaraCensor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMaraCensorDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 16:55
 */
public interface IMaraCensorDao {
    /**
     * @description：批量保存送检数据
     * @author：justin
     * @date：2020-01-09 17:03
     */
    public void saveMaraCensorBatch(List<TMaraCensor> censorList);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-01-09 18:01
     */
    public List<TMaraCensor> queryMaraCensorList(MaraCensor info);

    /**
     * @description：质检确认收货
     * @author：justin
     * @date：2020-01-10 13:57
     */
    public void updateMaraCensor(Map<String, Object> param);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-10 14:10
     */
    public Integer checkCensorState(@Param("id") String id, @Param("state") String state);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-10 14:10
     */
    public Integer checkCensorStateBatch(@Param("idList") List<String> idList, @Param("state") String state);

    /**
     * @description：保存质检结果
     * @author：justin
     * @date：2020-01-10 14:20
     */
    public void saveInspectResult(List<TMaraCensor> infoList);

    /**
     * @description：根据idList获取数据
     * @author：justin
     * @date：2020-01-10 15:00
     */
    public List<TMaraCensor> findMaraCensorByIdList(List<String> idList);
}
