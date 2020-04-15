package com.fl.web.model.system;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：Role
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 14:44
 */
@Getter
@Setter
public class Role extends PageModel {
    private String roleCode;//角色编码
    private String roleName;// 角色名

}
