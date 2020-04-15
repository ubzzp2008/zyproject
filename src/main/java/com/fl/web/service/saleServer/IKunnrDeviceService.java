package com.fl.web.service.saleServer;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.saleServer.TKunnrDevice;
import com.fl.web.model.saleServer.KunnrDevice;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IKunnrDeviceService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 09:33
 */
public interface IKunnrDeviceService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-14 09:44
     */
    public PageInfo<TKunnrDevice> queryKunnrDeviceList(KunnrDevice info);

    /**
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-10-14 10:41
     */
    public void deleteKunnrDeviceById(String id);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-10-14 10:42
     */
    public void saveKunnrDevice(TKunnrDevice info);

    /**
     * @description：根据客户编号和设备编号获取数据
     * @author：justin
     * @date：2019-10-14 10:42
     */
    public TKunnrDevice checkDataExist(String kunnr, String deviceCode);

    /**
     * @description：根据客户编码查询
     * @author：justin
     * @date：2019-11-15 14:50
     */
    public List<TKunnrDevice> searchDeviceListByKunnr(String info, String kunnr);

    /**
     * @description：根据客户编号获取设备
     * @author：justin
     * @date：2019-12-02 15:18
     */
    public List<TKunnrDevice> findDeviceListByKunnr(String kunnr);

    /**
     * @description：根据id更新flag字段
     * @author：justin
     * @date：2020-01-15 17:30
     */
    public void updateKunnrDeviceFlag(String id, String flag);
}
