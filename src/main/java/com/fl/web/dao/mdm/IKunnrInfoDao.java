package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TKunnrInfo;
import com.fl.web.model.mdm.KunnrInfo;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IKunnrInfoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 12:46
 */
public interface IKunnrInfoDao {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 11:21
     */
    public List<TKunnrInfo> queryKunnrInfoList(KunnrInfo info);

    /**
     * @desc：保存信息
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveKunnrInfo(TKunnrInfo info);

    /**
     * @desc：根据编码获取信息
     * @author：justin
     * @date：2019-01-10 12:59
     */
    public TKunnrInfo getKunnrInfoByKunnr(String kunnr);

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TKunnrInfo getKunnrInfoById(String id);

    /**
     * @description：修改信息
     * @author：justin
     * @date：2019-10-10 16:26
     */
    public void updateKunnrInfo(TKunnrInfo info);

    /**
     * @description：前端自动匹配带入查询物料数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    @Select("select kunnr,kunnr_name kunnrName,address from kunnr_info where status!='003' and (kunnr like #{info} or kunnr_name like #{info}) order by kunnr limit 20")
    @ResultType(TKunnrInfo.class)
    public List<TKunnrInfo> searchKunnrInfoList(String info);
}
