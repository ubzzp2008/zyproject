package com.fl.web.dao.system;

import com.fl.web.entity.system.TMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMenuDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 11:13
 */
public interface IMenuDao {
    /**
     * @description：根据用户id获取菜单
     * @author：justin
     * @date：2019-10-09 11:17
     */
//    @Select("select distinct tm.id,tm.name,tm.seq,tm.url,tm.levele,tm.pid,tm.icon,tm.description " +
//            " from t_user_position tup inner join t_position_role tpr on tup.pos_id = tpr.pos_id " +
//            " inner join t_role_menu trm on tpr.role_id = trm.role_id " +
//            " inner join t_menu tm on trm.menu_id = tm.id " +
//            " where tup.user_id = #{userId}  and ( #{currDate} between tup.start_date and tup.end_date ) and tm.lock_flag = '0' " +
//            " and tm.status != '003' and tup.status != '003'  order by tm.levele,tm.seq")
//    @ResultType(TMenu.class)
    public List<TMenu> findMenuByUserId(@Param("userId") String userId, @Param("posId") String posId, @Param("currDate") String currDate);

    /**
     * @description：获取所有菜单列表
     * @author：justin
     * @date：2019-10-12 15:57
     */
    public List<TMenu> queryMenuList();

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-10-12 15:56
     */
    public TMenu getMenuById(String id);

    /**
     * @description：保存菜单
     * @author：justin
     * @date：2019-10-12 15:56
     */
    public void saveMenu(TMenu menu);

    /**
     * @description：修改菜单
     * @author：justin
     * @date：2019-10-12 15:56
     */
    public void updateMenu(TMenu menu);

    /**
     * @description：根据id删除
     * @author：justin
     * @date：2019-10-12 15:56
     */
    public void deleteMenuById(Map<String, Object> map);

    /**
     * @description：根据pid获取菜单
     * @author：justin
     * @date：2019-10-12 15:56
     */
    public List<TMenu> getMenuByPid(String id);

    /**
     * @description：启用禁用菜单
     * @author：justin
     * @date：2019-10-12 16:43
     */
    public void lockOrUnlockMenu(TMenu menu);

    /**
     * @description：根据角色id获取菜单
     * @author：justin
     * @date：2019-10-14 15:15
     */
    public List<TMenu> getMenuByRoleId(String roleId);
}
