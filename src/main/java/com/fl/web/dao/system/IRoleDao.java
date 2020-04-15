package com.fl.web.dao.system;

import com.fl.web.entity.system.TRole;
import com.fl.web.model.system.Role;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRoleDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 14:44
 */
public interface IRoleDao {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 19:54
     */
    public List<TRole> queryRoleList(Role role);

    /**
     * @description：根据岗位id获取角色信息
     * @author：justin
     * @date：2019-10-14 15:47
     */
    public List<TRole> getRolesByPosId(String userId);

    /**
     * @desc：保存信息
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveRole(TRole role);

    /**
     * @desc：根据编码获取信息
     * @author：justin
     * @date：2019-01-10 12:59
     */
    public TRole getRoleByCode(String code);

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TRole getRoleById(String id);

    /**
     * @description：修改信息
     * @author：justin
     * @date：2019-10-10 16:26
     */
    public void updateRole(TRole role);
}
