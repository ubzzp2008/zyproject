package com.fl.web.service.saleServer;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.saleServer.TRepairUser;
import com.fl.web.model.saleServer.RepairUser;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRepairUserService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 16:13
 */
public interface IRepairUserService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-14 16:14
     */
    public PageInfo<TRepairUser> queryRepairUserList(RepairUser info);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-10-14 16:12
     */
    public void saveRepairUser(TRepairUser info);

    /**
     * @description：删除--逻辑删除
     * @author：justin
     * @date：2019-10-14 16:12
     */
    public void deleteRepairUserById(List<String> idList);

    /**
     * @description：根据用户名查找数据
     * @author：justin
     * @date：2019-10-15 16:50
     */
    public TRepairUser findRepairUserByUserName(String userName);

    /**
     * @description：获取所有维修员信息
     * @author：justin
     * @date：2019-10-17 09:26
     */
    public List<TRepairUser> findAllRepairUserList();
}
