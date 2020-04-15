package com.fl.web.dao.system;

import com.fl.web.entity.system.TRoleMenu;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRoleMenuDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 14:58
 */
public interface IRoleMenuDao {
    /**
     * @description：根据菜单id删除
     * @author：justin
     * @date：2019-10-12 16:29
     */
    public void deleteByMenuId(String menuId);

    /**
     * @description：根据角色id删除
     * @author：justin
     * @date：2019-10-12 16:30
     */
    public void deleteByRoleId(String roleId);

    /**
     * @description：批量保存菜单与角色的关系
     * @author：justin
     * @date：2019-10-12 16:59
     */
    public void saveRoleMenuBatch(List<TRoleMenu> roleMenus);

}
