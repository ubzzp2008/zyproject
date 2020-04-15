package com.fl.web.service.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TKunnrInfo;
import com.fl.web.model.mdm.KunnrInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IKunnrInfoService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 12:47
 */
public interface IKunnrInfoService {

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 11:18
     */
    public PageInfo<TKunnrInfo> queryKunnrInfoList(KunnrInfo info);

    /**
     * @desc：保存
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveKunnrInfo(TKunnrInfo info);

    /**
     * @desc：根据编码获取信息
     * @author：justin
     * @date：2019-01-10 12:57
     */
    public TKunnrInfo getKunnrInfoByKunnr(String kunnr);

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TKunnrInfo getKunnrInfoById(String id);

    /**
     * @description：根据id删除
     * @author：justin
     * @date：2019-10-10 16:23
     */
    public void deleteKunnrInfoById(String id);

    /**
     * @description：修改
     * @author：justin
     * @date：2019-10-10 16:24
     */
    public void updateKunnrInfo(TKunnrInfo info);

    /**
     * @description：前端自动匹配带入查询数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    public List<TKunnrInfo> searchKunnrInfoList(String info);
}
