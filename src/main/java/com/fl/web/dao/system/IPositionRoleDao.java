package com.fl.web.dao.system;


import com.fl.web.entity.system.TPositionRole;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IUserRole
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 14:58
 */
public interface IPositionRoleDao {
    /**
     * @description：根据岗位id删除
     * @author：justin
     * @date：2019-10-12 16:33
     */
    public void deleteByPosId(String posId);

    /**
     * @description：根据角色id删除
     * @author：justin
     * @date：2019-10-12 16:33
     */
    public void deleteByRoleId(String roleId);

    /**
     * @description：批量保存岗位与角色关系
     * @author：justin
     * @date：2019-11-19 16:33
     */
    public void savePositionRoles(List<TPositionRole> list);

}
