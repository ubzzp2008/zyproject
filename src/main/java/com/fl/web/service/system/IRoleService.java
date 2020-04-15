package com.fl.web.service.system;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.system.TRole;
import com.fl.web.entity.system.TRoleMenu;
import com.fl.web.model.system.Role;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRoleService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 15:02
 */
public interface IRoleService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 11:18
     */
    public PageInfo<TRole> queryRoleList(Role role);
    /**
     * @description：根据岗位id获取角色信息
     * @author：justin
     * @date：2019-10-14 15:47
     */
    public List<TRole> getRolesByPosId(String userId);

    /**
     * @desc：保存
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveRole(TRole role);

    /**
     * @desc：根据编码获取信息
     * @author：justin
     * @date：2019-01-10 12:57
     */
    public TRole getRoleByCode(String code);

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TRole getRoleById(String id);

    /**
     * @description：根据id删除
     * @author：justin
     * @date：2019-10-10 16:23
     */
    public void deleteRoleById(String id);

    /**
     * @description：修改
     * @author：justin
     * @date：2019-10-10 16:24
     */
    public void updateRole(TRole role);
/**
 *@description：保存
 *@author：justin
 *@date：2019-10-12 16:52
 */
    public void saveRoleMenus(List<TRoleMenu> list);
}
