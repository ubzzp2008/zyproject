package com.fl.web.dao.system;

import com.fl.web.entity.system.TUserPosition;
import com.fl.web.model.system.UserPosition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IUserPositionDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 17:09
 */
public interface IUserPositionDao {
    /**
     * @description：根据用户id检查可用岗位信息
     * @author：justin
     * @date：2019-11-18 20:28
     */
//    @Select("select count(1) from t_user_position where user_id=#{userId} and status!='003' and (#{currDate} between start_date and end_date)")
//    @ResultType(Integer.class)
//    public Integer findUserPosByUserId(@Param("userId") String userId, @Param("currDate") String currDate);

    /**
     * @description：保存人员岗位信息
     * @author：justin
     * @date：2019-11-19 14:54
     */
    public void saveUserPosition(TUserPosition userPosition);

    /**
     * @description：根据用户id批量修改数据
     * @author：justin
     * @date：2019-11-19 15:22
     */
    public void updateUserPositionByUserId(Map<String, Object> param);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-11-19 15:50
     */
    public List<TUserPosition> queryUserPositionList(UserPosition info);

    /**
     * @description：根据岗位id获取数据
     * @author：justin
     * @date：2019-11-19 16:42
     */
    @Select("select count(1) from t_user_position where pos_id=#{posId} and status!='003'")
    @ResultType(Integer.class)
    public Integer checkPositionByPosId(@Param("posId") String posId);

    /**
     * @description：根据岗位ID获取用户信息
     * @author：justin
     * @date：2019-12-13 15:16
     */
    @Select("select p.id,u.emp_code empCode,u.username,u.realname,p.pos_type posType,p.start_date startDate,p.end_date endDate " +
            " from t_user_position p inner join t_user_info u on u.id=p.user_id " +
            " where  p.pos_id=#{posId} and p.status!='003'")
    @ResultType(TUserPosition.class)
    public List<TUserPosition> getUserInfoByPosId(@Param("posId") String posId);

    /**
     * @description：批量保存
     * @author：justin
     * @date：2019-12-13 16:34
     */
    public void saveUserPositionBatch(List<TUserPosition> infoList);

    /**
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-12-13 16:46
     */
    public void deleteUserPositionBatch(Map<String, Object> param);

    /**
     * @description：根据用户获取岗位信息
     * @author：justin
     * @date：2019-12-16 09:28
     */
    @Select("select t.user_id userId,t.pos_id posId,t.pos_type posType,p.pos_code posCode,p.pos_name posName," +
            "p.org_id orgId,g.org_code orgCode,g.org_name orgName" +
            "  from t_user_position t inner join t_position p on p.id=t.pos_id left join t_org g on p.org_id=g.id " +
            " where t.user_id=#{userId} and p.status!='003' and t.status!='003' ")
    @ResultType(TUserPosition.class)
    public List<TUserPosition> getPositionByUserId(@Param("userId") String userId);

    /**
     * @description：根据pid获取下级所有用户
     * @author：justin
     * @date：2019-12-16 18:23
     */
    @Select("select u.username from t_user_position up inner join t_position p on p.id=up.pos_id " +
            " inner join t_user_info u on u.id=up.user_id " +
            " where (p.pid=#{pid} or p.id=#{pid}) and up.status!='003' ")
    @ResultType(String.class)
    public List<String> getUserNameByPid(@Param("pid") String pid);

    /**
     * @description：根据菜单id获取用户工号
     * @author：justin
     * @date：2019-12-18 09:36
     */
    @Select("select tui.emp_code from t_user_position tup inner join t_position_role tpr on tup.pos_id=tpr.pos_id " +
            " inner join t_role_menu trm on tpr.role_id =trm.role_id " +
            " inner join t_menu tm on trm.menu_id=tm.id " +
            " inner join t_user_info tui on tui.id=tup.user_id " +
            " where tm.id=#{menuId} " +
            " and tup.status!='003' and tui.lock_flag='0' and tui.emp_code like 'HC%' ")
    @ResultType(String.class)
    public List<String> findUserEmpCodeByMenuId(@Param("menuId") String menuId);
}
