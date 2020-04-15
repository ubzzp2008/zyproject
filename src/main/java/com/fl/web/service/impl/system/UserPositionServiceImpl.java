package com.fl.web.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.system.IUserPositionDao;
import com.fl.web.entity.system.TUserPosition;
import com.fl.web.model.system.UserPosition;
import com.fl.web.service.system.IUserPositionService;
import com.fl.web.utils.DateUtils;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：UserPositionServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 20:21
 */
@Service
@Transactional
public class UserPositionServiceImpl implements IUserPositionService {
    @Autowired
    private IUserPositionDao userPositionDao;

    @Override
    public PageInfo<TUserPosition> queryUserPositionList(UserPosition info) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TUserPosition> userList = userPositionDao.queryUserPositionList(info);
        PageInfo result = new PageInfo(userList);
        return result;
    }

    @Override
    public Integer checkPositionByPosId(String posId) {
        return userPositionDao.checkPositionByPosId(posId);
    }

    @Override
    public List<TUserPosition> getUserInfoByPosId(String posId) {
        return userPositionDao.getUserInfoByPosId(posId);
    }

    @Override
    public void saveUserPosition(List<TUserPosition> infoList) {
        for (TUserPosition up : infoList) {
            up.setId(UUIDUtil.get32UUID());
            up.setStartDate(DateUtils.formatDate());
            up.setEndDate("9999-12-31");
            up.setPosType(StaticParam.POS_S);//所有都为兼职岗位
            up.setStatus(StaticParam.ADD);
            up.setCreateBy(SessionUser.getUserName());
            up.setCreateDate(new Date());
        }
        userPositionDao.saveUserPositionBatch(infoList);
    }

    @Override
    public void deleteUserPosition(List<String> idList) {
        Map<String, Object> param = new HashMap<>();
        param.put("idList", idList);
        param.put("status", StaticParam.DEL);
        param.put("updateBy", SessionUser.getUserName());
        param.put("updateDate", new Date());
        param.put("endDate", DateUtils.addDate(-1));
        userPositionDao.deleteUserPositionBatch(param);
    }

    @Override
    public List<TUserPosition> getPositionByUserId(String userId) {
        return userPositionDao.getPositionByUserId(userId);
    }

    @Override
    public List<String> getUserNameByPid(String pid) {
        return userPositionDao.getUserNameByPid(pid);
    }

    @Override
    public List<String> findUserEmpCodeByMenuId(String menuId) {
        return userPositionDao.findUserEmpCodeByMenuId(menuId);
    }
}
