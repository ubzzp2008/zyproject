package com.fl.web.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.system.IPositionDao;
import com.fl.web.dao.system.IUserInfoDao;
import com.fl.web.dao.system.IUserPositionDao;
import com.fl.web.entity.system.TPosition;
import com.fl.web.entity.system.TUserInfo;
import com.fl.web.entity.system.TUserPosition;
import com.fl.web.model.system.User;
import com.fl.web.model.system.UserInfo;
import com.fl.web.service.system.IUserInfoService;
import com.fl.web.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：UserInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 10:10
 */
@Service
@Transactional
public class UserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private IUserInfoDao userInfoDao;
    @Autowired
    private IUserPositionDao userPositionDao;
    @Autowired
    private IPositionDao positionDao;
    @Value("${spring.profiles.active}")
    private String profiles;
//    @Value("${ldap.url}")
//    private String ldapUrl;
//    @Value("${ldap.username}")
//    private String ldapRootName;
//    @Value("${ldap.password}")
//    private String ldapPwd;

    @Override
    public TUserInfo checkUserExits(String userName, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", userName);
        map.put("password", PasswordUtil.getMD5(password));
        return userInfoDao.checkUserExits(map);
    }

    @Override
    public void saveUserInfo(TUserInfo userInfo) {
        String userId = UUIDUtil.get32UUID();
        userInfo.setId(userId);
        userInfo.setCreateBy(SessionUser.getUserName());
        userInfo.setCreateDate(new Date());
        userInfo.setStatus(StaticParam.ADD);
        userInfo.setPassword(PasswordUtil.getMD5(userInfo.getPassword()));
        //保存用户基本信息
        userInfoDao.saveUserInfo(userInfo);
        //构建人员与岗位信息并保存
        TUserPosition userPosition = initUserPosition(userId, userInfo.getPosId());
        userPositionDao.saveUserPosition(userPosition);
    }

    /**
     * @description：根据用户ID和岗位ID构建TUserPosition对象
     * @author：justin
     * @date：2019-11-19 15:03
     */
    private TUserPosition initUserPosition(String userId, String posId) {
        TPosition position = positionDao.getPositionById(posId);
        TUserPosition up = new TUserPosition();
        up.setId(UUIDUtil.get32UUID());
        up.setUserId(userId);
        up.setPosId(position.getId());
        up.setPosCode(position.getPosCode());
        up.setPosType(StaticParam.POS_M);
        up.setStartDate(DateUtils.formatDate());
        up.setEndDate("9999-12-31");
        up.setStatus(StaticParam.ADD);
        up.setCreateBy(SessionUser.getUserName());
        up.setCreateDate(new Date());
        return up;
    }

    @Override
    public PageInfo<TUserInfo> queryUserList(UserInfo user) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List<TUserInfo> userList = userInfoDao.queryUserList(user);
        PageInfo result = new PageInfo(userList);
        return result;
    }

    @Override
    public PageInfo<TUserInfo> queryUserForPosition(UserInfo info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TUserInfo> userList = userInfoDao.queryUserForPosition(info);
        PageInfo result = new PageInfo(userList);
        return result;
    }

    @Override
    public TUserInfo getUserInfoById(String id) {
        return userInfoDao.getUserInfoById(id);
    }

    @Override
    public TUserInfo getUserByUserName(String userName) {
        return userInfoDao.getUserByUserName(userName);
    }

    @Override
    public void updateUserInfo(TUserInfo userInfo) {
        //根据id获取用户信息
        TUserInfo oldUser = userInfoDao.getUserInfoById(userInfo.getId());
        //检查岗位是否做了修改，修改前的posId与修改后的posId不一致
        if (!StringUtil.equals(oldUser.getPosId(), userInfo.getPosId())) {
            //将此用户之前的所有岗位截止并做删除标识
            Map<String, Object> param = new HashMap<>();
            param.put("userId", userInfo.getId());
            param.put("posId", oldUser.getPosId());
            param.put("status", StaticParam.DEL);
            param.put("updateBy", SessionUser.getUserName());
            param.put("updateDate", new Date());
            param.put("endDate", DateUtils.addDate(-1));
            userPositionDao.updateUserPositionByUserId(param);
            //构建新的用户与岗位关系数据并保存
            TUserPosition up = initUserPosition(userInfo.getId(), userInfo.getPosId());
            userPositionDao.saveUserPosition(up);
        }
        //修改用户信息
        userInfo.setUpdateBy(SessionUser.getUserName());
        userInfo.setUpdateDate(new Date());
        userInfo.setStatus(StaticParam.MOD);
        if (StringUtils.isNotEmpty(userInfo.getPassword())) {
            userInfo.setPassword(PasswordUtil.getMD5(userInfo.getPassword()));
        }
        userInfoDao.updateUserInfo(userInfo);
    }

    @Override
    public void deleteUserInfoById(String id) {
        TUserInfo user = new TUserInfo();
        user.setId(id);
        user.setUpdateBy(SessionUser.getUserName());
        user.setUpdateDate(new Date());
        user.setStatus(StaticParam.DEL);
        userInfoDao.updateUserInfo(user);
        //将此用户之前的所有岗位截止并做删除标识
        Map<String, Object> param = new HashMap<>();
        param.put("userId", id);
        param.put("status", StaticParam.DEL);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        param.put("endDate", DateUtils.addDate(-1));
        userPositionDao.updateUserPositionByUserId(param);
    }

    @Override
    public List<TUserInfo> getRepairUser() {
        return userInfoDao.getRepairUser(StaticParam.REPAIRPOS);
    }

    @Override
    public Integer checkOldPwd(String userId, String oldPwd) {
        return userInfoDao.checkOldPwd(userId, PasswordUtil.getMD5(oldPwd));
    }

    @Override
    public void updatePasswordSelf(String userId, String userName, String newPwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", PasswordUtil.getMD5(newPwd));
        map.put("status", StaticParam.MOD);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        userInfoDao.updatePasswordSelf(map);//修改本地密码
//        if (StringUtil.equals("prd", profiles)) {//正式环境时，需要修改远程密码
//            LdapUtil.updateNasPassword(ldapUrl, ldapRootName, ldapPwd, userName, newPwd);//修改远程nas密码
//        }
    }

    @Override
    public void updateUserInfoSelf(TUserInfo user) {
        user.setStatus(StaticParam.MOD);
        user.setUpdateBy(SessionUser.getUserName());
        user.setUpdateDate(new Date());
        userInfoDao.updateUserInfoSelf(user);
    }

    @Override
    public void initPassword(TUserInfo user) {
        //初始化密码
        user.setUpdateBy(SessionUser.getUserName());
        user.setUpdateDate(new Date());
        user.setStatus(StaticParam.MOD);
        user.setPassword(PasswordUtil.getMD5(user.getPassword()));
        userInfoDao.updateUserInfo(user);
    }

    @Override
    public void lockOrUnlockUser(TUserInfo user) {
        //初始化密码
        user.setUpdateBy(SessionUser.getUserName());
        user.setUpdateDate(new Date());
        user.setStatus(StaticParam.MOD);
        userInfoDao.updateUserInfo(user);
    }

    @Override
    public void syncNasUserInfo(User nasUser, String password) {
        //根据用户名判断用户是否存在
        TUserInfo info = userInfoDao.getUserByUserName(nasUser.getUserName());
        if (info != null) {
            info.setPassword(PasswordUtil.getMD5(password));
            info.setRealName(nasUser.getRealName());
            info.setEmpCode(nasUser.getEmpCode());
            info.setEmail(nasUser.getEmail());
            info.setPhone(nasUser.getPhone());
            info.setStatus(StaticParam.MOD);
            info.setLockFlag(nasUser.getLockFlag());
            info.setUpdateDate(new Date());
            info.setUpdateBy("NAS");
            userInfoDao.syncNasUserInfo(info);
        } else {
            info = new TUserInfo();
            info.setId(UUIDUtil.get32UUID());
            info.setUserName(nasUser.getUserName());
            info.setPassword(PasswordUtil.getMD5(password));
            info.setRealName(nasUser.getRealName());
            info.setEmpCode(nasUser.getEmpCode());
            info.setEmail(nasUser.getEmail());
            info.setPhone(nasUser.getPhone());
            info.setStatus(StaticParam.ADD);
            info.setLockFlag(nasUser.getLockFlag());
            info.setCreateDate(new Date());
            info.setCreateBy("NAS");
            userInfoDao.saveUserInfo(info);
        }
    }

}
