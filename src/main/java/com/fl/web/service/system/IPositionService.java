package com.fl.web.service.system;

import com.fl.web.entity.system.TPosition;
import com.fl.web.model.base.BaseTree;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IPositionService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 17:11
 */
public interface IPositionService {
    /**
     * @description：根据orgId获取岗位信息
     * @author：justin
     * @date：2019-12-13 14:28
     */
    public List<TPosition> getPositionByOrgId(String orgId);

    /**
     * @description：根据code获取数据
     * @author：justin
     * @date：2019-11-18 17:48
     */
    public TPosition getPositionByCode(String posCode);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-11-18 17:48
     */
    public TPosition getPositionById(String id);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-11-18 17:48
     */
    public void savePosition(TPosition position);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-11-18 17:48
     */
    public void updatePosition(TPosition position);

    /**
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-11-18 17:48
     */
    public void deletePositionById(String id);

    /**
     * @description：获取级联选择需要的岗位信息
     * @author：justin
     * @date：2019-11-19 09:40
     */
    public List<BaseTree> findAllPositionList();

    /**
     * @description：根据父级id获取岗位信息
     * @author：justin
     * @date：2019-11-28 16:03
     */
    public List<TPosition> getPositionByPid(String pid);


}
