package com.fl.web.entity.system;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TRole
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 14:45
 */
@Getter
@Setter
public class TRole extends BaseEntity {
    private String roleCode;//角色编码
    private String roleName;// 角色名
    private String roleDesc;// 角色描述
    private String posId;//岗位id

}
