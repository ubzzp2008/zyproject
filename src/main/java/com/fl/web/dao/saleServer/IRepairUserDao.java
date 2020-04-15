package com.fl.web.dao.saleServer;

import com.fl.web.entity.saleServer.TRepairUser;
import com.fl.web.model.saleServer.RepairUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRepairUserDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 16:00
 */
public interface IRepairUserDao {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-14 16:12
     */
    public List<TRepairUser> queryRepairUserList(RepairUser info);

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
    public void deleteRepairUser(Map<String, Object> map);

    /**
     * @description：根据用户名查找数据
     * @author：justin
     * @date：2019-10-15 16:51
     */
    @Select("select id,username,realname,telphone,note from repair_user where username=#{userName} and status!='003'")
    @ResultType(TRepairUser.class)
    public TRepairUser findRepairUserByUserName(@Param(value = "userName") String userName);
}
