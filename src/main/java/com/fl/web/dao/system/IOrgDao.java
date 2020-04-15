package com.fl.web.dao.system;

import com.fl.web.entity.system.TOrg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrgDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:34
 */
public interface IOrgDao {
    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-13 09:42
     */
    public void saveOrg(TOrg org);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
    public void updateOrg(TOrg org);

    /**
     * @description：删除数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
    @Delete("delete from t_org  where id=#{id}")
    public void deleteOrgById(@Param("id") String id);

    /**
     * @description：根据编码获取数据
     * @author：justin
     * @date：2019-12-13 09:53
     */
    @Select("select id,org_code orgCode,org_name orgName,pid,note from t_org where org_code=#{code}")
    @ResultType(TOrg.class)
    public TOrg getOrgByCode(@Param("code") String code);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:09
     */
    @Select("select id,org_code orgCode,org_name orgName,pid,note from t_org where id=#{id}")
    @ResultType(TOrg.class)
    public TOrg getOrgById(@Param("id") String id);

    /**
     * @description：根据pid获取数据
     * @author：justin
     * @date：2019-12-13 10:29
     */
    @Select("select id,org_code orgCode,org_name orgName,pid,note from t_org where pid=#{pid}")
    @ResultType(TOrg.class)
    public List<TOrg> getOrgByPid(@Param("pid") String pid);

    /**
     * @description：获取所有组织信息
     * @author：justin
     * @date：2019-12-13 10:49
     */
    @Select("select id,org_code orgCode,org_name orgName,pid,note from t_org")
    @ResultType(TOrg.class)
    public List<TOrg> findAllOrgList();
}
