package com.fl.web.dao.saleServer;

import com.fl.web.entity.saleServer.TKunnrDevice;
import com.fl.web.model.saleServer.KunnrDevice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IKunnrDeviceDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 09:14
 */
public interface IKunnrDeviceDao {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-14 09:45
     */
    public List<TKunnrDevice> queryKunnrDeviceList(KunnrDevice info);

    /**
     * @description：删除--逻辑删除
     * @author：justin
     * @date：2019-10-14 10:47
     */
    public void deleteKunnrDevice(Map<String, Object> map);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-10-14 10:47
     */
    public void saveKunnrDevice(TKunnrDevice info);

    /**
     * @description：根据客户编码和
     * @author：justin
     * @date：2019-10-14 10:46
     */
    public TKunnrDevice checkDataExist(@Param("kunnr") String kunnr, @Param("deviceSN") String deviceSN);

    /**
     * @description：根据客户编码获取数据
     * @author：justin
     * @date：2019-11-15 14:51
     */
    @Select("select kd.device_sn deviceSN,di.device_name deviceName,di.device_norms deviceNorms from kunnr_device kd  left join device_info di on kd.device_sn=di.device_sn " +
            " where kd.status!='003' and kd.flag='0' and kd.kunnr=#{kunnr} and (di.device_sn like #{info} or di.device_name like #{info}) limit 50")
    @ResultType(TKunnrDevice.class)
    public List<TKunnrDevice> searchDeviceListByKunnr(@Param("info") String info, @Param("kunnr") String kunnr);

    @Select("select kd.device_sn deviceSN,di.device_name deviceName,di.device_norms deviceNorms from kunnr_device kd  left join device_info di on kd.device_sn=di.device_sn " +
            " where kd.status!='003' and kd.flag='0' and kd.kunnr=#{kunnr} ")
    @ResultType(TKunnrDevice.class)
    public List<TKunnrDevice> findDeviceListByKunnr(@Param("kunnr") String kunnr);

    /**
     * @description：启用禁用
     * @author：justin
     * @date：2020-01-15 17:31
     */
    public void updateKunnrDeviceFlag(Map<String, Object> map);
}
