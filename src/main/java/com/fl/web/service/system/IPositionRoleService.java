package com.fl.web.service.system;

import com.fl.web.entity.system.TPositionRole;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IPositionRoleService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-19 16:30
 */
public interface IPositionRoleService {
    /**
     * @description：批量保存岗位与角色关系
     * @author：justin
     * @date：2019-11-19 16:32
     */
    public void savePositionRoles(List<TPositionRole> list);
}
