package com.fl.web.service.mdm;

import com.fl.web.entity.mdm.TAttachInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IAttachInfoService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-05 17:05
 */
public interface IAttachInfoService {
    /**
     * @description：批量保存附件信息
     * @author：justin
     * @date：2019-12-05 17:12
     */
    public void saveAttachInfoBatch(List<TAttachInfo> list);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-06 14:33
     */
    public void saveAttachInfo(TAttachInfo info);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-06 15:39
     */
    public TAttachInfo getAttachInfoById(String id);

    /**
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-12-06 15:40
     */
    public void deleteAttachInfoById(String id);

    /**
     * @description：根据单据号获取数据
     * @author：justin
     * @date：2019-12-06 15:39
     */
    public List<TAttachInfo> getAttachInfoByOrderNo(String orderNo);

    /**
     * @description：根据tableId获取数据
     * @author：justin
     * @date：2019-12-06 15:39
     */
    public List<TAttachInfo> getAttachInfoByTableId(String tableId);

    /**
     * @description：根据tableId和flag检查数据是够存在
     * @author：justin
     * @date：2019-12-10 14:40
     */
    public Integer checkAttachInfoByTableId(String tableId, String flag);
}
