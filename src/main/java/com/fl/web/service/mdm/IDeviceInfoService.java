package com.fl.web.service.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TDeviceInfo;
import com.fl.web.model.mdm.DeviceInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IDeviceService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 16:02
 */
public interface IDeviceInfoService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 11:18
     */
    public PageInfo<TDeviceInfo> queryDeviceInfoList(DeviceInfo info);

    /**
     * @desc：保存
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveDeviceInfo(TDeviceInfo info);

    /**
     * @desc：根据编码获取信息
     * @author：justin
     * @date：2019-01-10 12:57
     */
    public TDeviceInfo getDeviceInfoBySN(String sn);

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TDeviceInfo getDeviceInfoById(String id);

    /**
     * @description：根据id删除
     * @author：justin
     * @date：2019-10-10 16:23
     */
    public void deleteDeviceInfoById(String id);

    /**
     * @description：修改
     * @author：justin
     * @date：2019-10-10 16:24
     */
    public void updateDeviceInfo(TDeviceInfo info);

    /**
     * @description：前端自动匹配带入查询数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    public List<TDeviceInfo> searchDeviceInfoList(String info);

    /**
     * @description：获取所有设备信息
     * @author：justin
     * @date：2019-12-02 14:20
     */
    public List<TDeviceInfo> findAllDeviceInfoList();
}
