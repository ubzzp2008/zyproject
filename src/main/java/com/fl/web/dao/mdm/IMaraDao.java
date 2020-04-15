package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TMara;
import com.fl.web.model.mdm.Mara;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMaraDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 11:49
 */
public interface IMaraDao {
    /**
     * @description：分页查询物料信息
     * @author：justin
     * @date：2019-10-10 11:21
     */
    public List<TMara> queryMaraList(Mara mara);

    /**
     * @desc：保存物料基础信息
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveMara(TMara mara);

    /**
     * @desc：根据物料编码获取信息
     * @author：justin
     * @date：2019-01-10 12:59
     */
    public TMara getMaraByMatnr(String matnr);

    /**
     * @description：根据id获取物料信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TMara getMaraById(String id);

    /**
     * @description：修改物料信息
     * @author：justin
     * @date：2019-10-10 16:26
     */
    public void updateMara(TMara mara);

    /**
     * @description：前端自动匹配带入查询物料数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    @Select("select t.maktl,t.name maktlName,m.matnr,m.maktx,m.norms,m.unit,m.manufacturer,m.quality_type qualityType " +
            " from mara m left join mara_type t on m.maktl=t.maktl " +
            "where m.status!='003'  and (m.matnr like #{info} or m.maktx like #{info}) limit 20")
    @ResultType(TMara.class)
    public List<TMara> searchMaraList(String info);

    /**
     * @description：批量保存数据
     * @author：justin
     * @date：2019-11-26 11:52
     */
    public void saveMaraBatch(List<TMara> maraList);

    /**
     * @description：查询金蝶系统物料数据--跨库查询hcsys8081中的kd_mara表
     * @author：justin
     * @date：2019-12-12 09:28
     */
    public List<TMara> searchKdMaraList(Mara mara);

    /**
     * @description：修改参考价格
     * @author：justin
     * @date：2020-01-08 17:04
     */
    @Update("update mara set price=#{price} where matnr=#{matnr}")
    public void updateMaraPrice(@Param("matnr") String matnr, @Param("price") BigDecimal price);
}
