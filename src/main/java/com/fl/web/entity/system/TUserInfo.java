package com.fl.web.entity.system;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TUserInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 14:52
 */
@Getter
@Setter
public class TUserInfo extends BaseEntity {
    /**
     * 登陆账号
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 主岗位ID
     */
    private String posId;
    /**
     * 主岗位编码
     */
    private String posCode;
    /**
     * 主岗位名称
     */
    private String posName;
    /**
     * 性别：0女1男
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 0启用，1禁用
     */
    private String lockFlag;

    private String email;
    private String empCode;
    private String orgId;
    private String orgCode;
    private String orgName;

}
