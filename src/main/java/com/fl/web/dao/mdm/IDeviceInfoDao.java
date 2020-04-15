package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TDeviceInfo;
import com.fl.web.model.mdm.DeviceInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IDeviceInfoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 15:51
 */
public interface IDeviceInfoDao {

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-11 15:59
     */
    public List<TDeviceInfo> queryDeviceInfoList(DeviceInfo info);

    /**
     * @description：保存信息
     * @author：justin
     * @date：2019-10-11 15:59
     */
    public void saveDeviceInfo(TDeviceInfo info);

    /**
     * @description：根据SN号获取数据
     * @author：justin
     * @date：2019-10-11 16:01
     */
    public TDeviceInfo getDeviceInfoBySN(String sn);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-10-11 16:01
     */
    public TDeviceInfo getDeviceInfoById(String id);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-10-11 16:02
     */
    public void updateDeviceInfo(TDeviceInfo info);

    /**
     * @description：前端自动匹配带入查询物料数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    @Select("select device_sn deviceSN,device_name deviceName,device_norms deviceNorms " +
            " from device_info where status!='003' and (device_sn like #{info} or device_name like #{info}) limit 50")
    @ResultType(TDeviceInfo.class)
    public List<TDeviceInfo> searchDeviceInfoList(@Param("info") String info);

    /**
     * @description：获取所有设备列表
     * @author：justin
     * @date：2019-12-02 14:21
     */
    @Select("select device_sn deviceSN,device_name deviceName,device_norms deviceNorms from device_info where status!='003' ")
    @ResultType(TDeviceInfo.class)
    public List<TDeviceInfo> findAllDeviceInfoList();
}
