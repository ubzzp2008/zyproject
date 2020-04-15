package com.fl.web.service.system;

import com.fl.web.entity.system.TOrg;
import com.fl.web.model.base.BaseTree;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrgService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:47
 */
public interface IOrgService {
    /**
     * @description：根据编码获取信息
     * @author：justin
     * @date：2019-12-13 09:51
     */
    public TOrg getOrgByCode(String code);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:08
     */
    public TOrg getOrgById(String id);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-13 09:51
     */
    public void saveOrg(TOrg org);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-12-13 10:01
     */
    public void updateOrg(TOrg org);

    /**
     * @description：根据父id获取下级组织信息
     * @author：justin
     * @date：2019-12-13 10:21
     */
    public List<TOrg> getOrgByPid(String pid);

    /**
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-12-13 10:22
     */
    public void deleteOrgById(String id);

    /**
     * @description：获取组织信息，树形结构
     * @author：justin
     * @date：2019-12-13 10:47
     */
    public List<BaseTree> findAllOrgList();
}
