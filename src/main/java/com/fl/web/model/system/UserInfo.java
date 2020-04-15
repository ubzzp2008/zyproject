package com.fl.web.model.system;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：UserInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 14:53
 */
@Getter
@Setter
public class UserInfo extends PageModel {
    /**
     * 登陆账号
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    private String email;
    private String empCode;
    private String posId;

}
