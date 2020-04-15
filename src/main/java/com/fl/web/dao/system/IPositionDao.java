package com.fl.web.dao.system;

import com.fl.web.entity.system.TPosition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IPositionDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 17:07
 */
public interface IPositionDao {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-11-18 17:14
     */
    public List<TPosition> getPositionByOrgId(String orgId);

    /**
     * @description：获取所有岗位信息
     * @author：justin
     * @date：2019-11-19 09:53
     */
    @Select("select t.id,t.pos_code posCode,t.pos_name posName,t.pid,t.note from t_position t where t.status!='003'")
    @ResultType(TPosition.class)
    public List<TPosition> findAllPositionList();

    /**
     * @description：根据code获取数据
     * @author：justin
     * @date：2019-11-18 17:48
     */
    @Select("select t.id,t.pos_code posCode,t.pos_name posName,t.pid,tp.pos_code pCode,tp.pos_name pName,t.note " +
            " from t_position t left join t_position tp on t.pid=tp.id where t.pos_code=#{posCode} and t.status!='003'")
    @ResultType(TPosition.class)
    public TPosition getPositionByCode(@Param("posCode") String posCode);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-11-18 17:48
     */
    @Select("select t.id,t.pos_code posCode,t.pos_name posName,t.pid,tp.pos_code pCode,tp.pos_name pName,t.note " +
            " from t_position t left join t_position tp on t.pid=tp.id where t.id=#{id} and t.status!='003'")
    @ResultType(TPosition.class)
    public TPosition getPositionById(@Param("id") String id);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-11-18 17:54
     */
    public void savePosition(TPosition position);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-11-18 18:08
     */
    public void updatePosition(TPosition position);

    /**
     * @description：根据父级id获取岗位信息
     * @author：justin
     * @date：2019-11-28 16:05
     */
    @Select("select id,pos_code posCode,pos_name posName,pid,note from t_position where pid=#{pid} and status!='003'")
    @ResultType(TPosition.class)
    public List<TPosition> getPositionByPid(@Param("pid") String pid);

}
