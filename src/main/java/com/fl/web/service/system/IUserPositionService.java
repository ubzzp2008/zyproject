package com.fl.web.service.system;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.system.TUserPosition;
import com.fl.web.model.system.UserPosition;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IUserPositionService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 20:20
 */
public interface IUserPositionService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-11-19 15:49
     */
    public PageInfo<TUserPosition> queryUserPositionList(UserPosition info);

    /**
     * @description：根据岗位id获取数据
     * @author：justin
     * @date：2019-11-19 16:41
     */
    public Integer checkPositionByPosId(String posId);

    /**
     * @description：根据岗位id获取用户信息
     * @author：justin
     * @date：2019-12-13 15:15
     */
    public List<TUserPosition> getUserInfoByPosId(String posId);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-13 15:58
     */
    public void saveUserPosition(List<TUserPosition> infoList);

    /**
     * @description：删除数据
     * @author：justin
     * @date：2019-12-13 15:58
     */
    public void deleteUserPosition(List<String> idList);

    /**
     * @description：根据用户id获取岗位信息
     * @author：justin
     * @date：2019-12-13 17:33
     */
    public List<TUserPosition> getPositionByUserId(String userId);

    /**
     * @description：根据pid获取下级所有用户
     * @author：justin
     * @date：2019-12-16 18:21
     */
    public List<String> getUserNameByPid(String pid);

    /**
     * @description：根据菜单id获取用户工号
     * @author：justin
     * @date：2019-12-18 09:35
     */
    public List<String> findUserEmpCodeByMenuId(String menuId);
}
