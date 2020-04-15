package com.fl.web.service.qm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.qm.TMaraCensor;
import com.fl.web.model.qm.MaraCensor;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMaraCensorService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 16:56
 */
public interface IMaraCensorService {
    /**
     * @description：分页查询送检物料信息
     * @author：justin
     * @date：2020-01-09 18:00
     */
    public PageInfo<TMaraCensor> queryMaraCensorList(MaraCensor info);

    /**
     * @description：质检确认收货
     * @author：justin
     * @date：2020-01-10 13:54
     */
    public void qmConfirmReceiveCensor(List<String> idList, String state);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-10 14:08
     */
    public Integer checkCensorState(String id, String state);

    /**
     * @description：检查数据状态
     * @author：justin
     * @date：2020-01-10 14:08
     */
    public Integer checkCensorStateBatch(List<String> idList, String state);

    /**
     * @description：保存质检结果
     * @author：justin
     * @date：2020-01-10 14:18
     */
    public void saveInspectResult(List<TMaraCensor> infoList);

    /**
     * @description：根据id集合修改状态
     * @author：justin
     * @date：2020-01-10 14:28
     */
    public void updateMaraCensor(List<String> idList, String state);

    /**
     * @description：创建质检单
     * @author：justin
     * @date：2020-01-10 14:56
     */
    public void saveCensorShip(List<String> idList);

    /**
     * @description：质检收货退回
     * @author：justin
     * @date：2020-01-14 17:15
     */
    public void qmRejectReceiveCensor(List<String> idList, String reason);

    /**
     * @desc：质检转待入库
     * @author：justin
     * @date：2020/2/5 16:48
     */
    public void comfirmToStore(List<String> idList, String state);
}
