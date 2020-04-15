package com.fl.web.dao.system;

import com.fl.web.entity.system.TUserInfo;
import com.fl.web.model.system.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IUserInfoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 10:11
 */
public interface IUserInfoDao {
    /**
     * @description：根据用户名密码检查用户是否存在
     * @author：justin
     * @date：2019-10-10 09:25
     */
    public TUserInfo checkUserExits(Map<String, Object> map);

    /**
     * @description：保存用户
     * @author：justin
     * @date：2019-10-10 09:24
     */
    public void saveUserInfo(TUserInfo user);

    /**
     * @description：分页查询用户信息
     * @author：justin
     * @date：2019-10-10 09:43
     */
    public List<TUserInfo> queryUserList(UserInfo user);

    /**
     * @description：根据ID获取用户信息
     * @author：justin
     * @date：2019-10-10 13:50
     */
    public TUserInfo getUserInfoById(String id);

    /**
     * @description：修改用户信息
     * @author：justin
     * @date：2019-10-10 13:53
     */
    public void updateUserInfo(TUserInfo userInfo);

    /**
     * @description：根据用户名获取用户信息
     * @author：justin
     * @date：2019-10-10 15:17
     */
    public TUserInfo getUserByUserName(String userName);

    /**
     * @description：获取未配置的维修人员
     * @author：justin
     * @date：2019-10-14 16:20
     */
    public List<TUserInfo> getRepairUser(String posCode);

    /**
     * @description：检查原始密码是否正确
     * @author：justin
     * @date：2019-11-21 10:10
     */
    @Select("select count(1) from t_user_info where id=#{userId} and password=#{pwd} and status!='003'")
    @ResultType(Integer.class)
    public Integer checkOldPwd(@Param("userId") String userId, @Param("pwd") String pwd);

    /**
     * @description：根据用户id修改密码
     * @author：justin
     * @date：2019-11-21 10:11
     */
    @Update("update t_user_info set password=#{password},status=#{status},update_by=#{updateBy},update_date=#{updateDate} where id=#{userId}")
    public void updatePasswordSelf(Map<String, Object> map);

    /**
     * @description：个人信息修改
     * @author：justin
     * @date：2019-11-22 10:50
     */
    @Update("update t_user_info set realname=#{realName},sex=#{sex},birthday=#{birthday},phone=#{phone},status=#{status},update_by=#{updateBy},update_date=#{updateDate} " +
            " where id=#{id}")
    public void updateUserInfoSelf(TUserInfo user);

    /**
     * @description：登录时同步nas用户信息
     * @author：justin
     * @date：2019-12-03 14:47
     */
    @Update("update t_user_info set realname=#{realName},emp_code=#{empCode},email=#{email},phone=#{phone},lock_flag=#{lockFlag}," +
            "password=#{password},status=#{status},update_by=#{updateBy},update_date=#{updateDate} " +
            " where id=#{id}")
    public void syncNasUserInfo(TUserInfo user);

    /**
     * @description：分页查询可用用户
     * @author：justin
     * @date：2019-12-13 16:09
     */
    public List<TUserInfo> queryUserForPosition(UserInfo info);
}
