package com.fl.web.service.saleServer;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.saleServer.TRepairMara;
import com.fl.web.model.saleServer.RepairMara;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRepairMaraService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-30 14:59
 */
public interface IRepairMaraService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-31 14:05
     */
    public PageInfo<TRepairMara> queryRepairMaraList(RepairMara info);

    /**
     * @description：修改
     * @author：justin
     * @date：2019-11-01 10:47
     */
    public void updateRepairMara(Map<String, Object> param);

    /**
     * @description：确认收货
     * @author：justin
     * @date：2019-11-01 11:23
     */
    public void saveReceiveMara(List<TRepairMara> maraList);

    /**
     * @description：根据单号集合获取数据
     * @author：justin
     * @date：2019-12-23 13:05
     */
    public List<TRepairMara> getRepairMaraByOrderNoList(List<String> orderNoList);
}
