package com.fl.web.service.system;

import com.fl.web.entity.system.TMenu;
import com.fl.web.model.base.ModuleTree;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMenuService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 11:03
 */
public interface IMenuService {
    /**
     * @description：根据用户id获取菜单
     * @author：justin
     * @date：2019-10-09 11:05
     */
    public List<ModuleTree> findMenuByUserId(String userId, String posId);

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
    public void deleteMenuById(String id);

    /**
     * @description：根据pid获取菜单
     * @author：justin
     * @date：2019-10-12 15:56
     */
    public List<TMenu> getMenuByPid(String pid);

    /**
     * @description：启用禁用菜单
     * @author：justin
     * @date：2019-10-12 16:42
     */
    public void lockOrUnlockMenu(TMenu menu);

    /**
     * @description：根据角色id获取菜单
     * @author：justin
     * @date：2019-10-14 15:14
     */
    public List<TMenu> getMenuByRoleId(String roleId);
}
