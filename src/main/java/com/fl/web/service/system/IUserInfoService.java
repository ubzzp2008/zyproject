package com.fl.web.service.system;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.system.TUserInfo;
import com.fl.web.model.system.User;
import com.fl.web.model.system.UserInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IUserInfoService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 10:09
 */
public interface IUserInfoService {
    /**
     * @description：根据用户名和密码检查用户是否存在
     * @author：justin
     * @date：2019-10-10 09:22
     */
    public TUserInfo checkUserExits(String userName, String password);

    /**
     * @description：保存用户信息
     * @author：justin
     * @date：2019-10-10 09:23
     */
    public void saveUserInfo(TUserInfo userInfo);

    /**
     * @description：分页查询用户信息
     * @author：justin
     * @date：2019-10-10 09:41
     */
    public PageInfo<TUserInfo> queryUserList(UserInfo user);

    /**
     * @description：根据ID获取用户信息
     * @author：justin
     * @date：2019-10-10 13:50
     */
    public TUserInfo getUserInfoById(String id);

    /**
     * @description：根据用户名获取用户信息
     * @author：justin
     * @date：2019-10-10 15:17
     */
    public TUserInfo getUserByUserName(String userName);

    /**
     * @description：修改用户信息
     * @author：justin
     * @date：2019-10-10 13:53
     */
    public void updateUserInfo(TUserInfo userInfo);

    /**
     * @description：根据id删除用户信息
     * @author：justin
     * @date：2019-10-10 13:59
     */
    public void deleteUserInfoById(String id);

    /**
     * @description：获取未配置的维修人员
     * @author：justin
     * @date：2019-10-14 16:20
     */
    public List<TUserInfo> getRepairUser();

    /**
     * @description：验证原始密码是否正确
     * @author：justin
     * @date：2019-11-21 10:09
     */
    public Integer checkOldPwd(String userId, String oldPwd);

    /**
     * @description：根据id修改密码
     * @author：justin
     * @date：2019-11-21 10:09
     */
    public void updatePasswordSelf(String userId, String userName, String newPwd);

    /**
     * @description：个人信息修改
     * @author：justin
     * @date：2019-11-22 10:50
     */
    public void updateUserInfoSelf(TUserInfo user);

    /**
     * @description：初始化密码
     * @author：justin
     * @date：2019-12-03 09:43
     */
    public void initPassword(TUserInfo user);

    /**
     * @description：启用/禁用
     * @author：justin
     * @date：2019-12-03 09:48
     */
    public void lockOrUnlockUser(TUserInfo user);

    /**
     * @description：同步nas用户信息
     * @author：justin
     * @date：2019-12-03 14:46
     */
    public void syncNasUserInfo(User nasUser, String password);

    /**
     * @description：分页查询可用用户
     * @author：justin
     * @date：2019-12-13 16:07
     */
    public PageInfo<TUserInfo> queryUserForPosition(UserInfo info);
}
