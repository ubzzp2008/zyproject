package com.fl.web.model.system;

import com.fl.web.entity.system.TUserPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：User
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-10 10:55
 */
@Getter
@Setter
public class User {
    private String id;
    /**
     * 登陆账号
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String realName;
    private String posId;
    private String posCode;
    private String posName;
    private String orgId;
    private String orgCode;
    private String orgName;
    private List<TUserPosition> posList;
    private String empCode;//员工编号
    private String email;//邮箱
    private String phone;
    private String lockFlag;
    private String sex;
    private String birthday;

}
