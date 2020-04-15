package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TQualityInfo;
import com.fl.web.model.mdm.QualityInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IQualityInfoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 10:19
 */
public interface IQualityInfoDao {

    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-01-09 10:25
     */
    public List<TQualityInfo> queryQualityInfoList(QualityInfo info);

    /**
     * @description：保存
     * @author：justin
     * @date：2020-01-09 10:25
     */
    public void saveQualityInfo(TQualityInfo info);

    /**
     * @description：修改
     * @author：justin
     * @date：2020-01-09 10:25
     */
    public void updateQualityInfo(TQualityInfo info);

    /**
     * @description：根据id删除
     * @author：justin
     * @date：2020-01-09 10:27
     */
    @Delete("delete from quality_info where id=#{id}")
    public void deleteQualityInfoById(@Param("id") String id);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2020-01-09 10:46
     */
    @Select("select id,code,name,flag,sortnum from quality_info where id=#{id}")
    @ResultType(TQualityInfo.class)
    public TQualityInfo getQualityInfoById(@Param("id") String id);

    /**
     * @description：根据code获取数据
     * @author：justin
     * @date：2020-01-09 10:46
     */
    @Select("select id,code,name,flag,sortnum from quality_info where code=#{code}")
    @ResultType(TQualityInfo.class)
    public TQualityInfo getQualityInfoByCode(@Param("code") String code);
}
