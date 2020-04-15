package com.fl.web.service.impl.system;

import com.fl.web.dao.system.IOrgDao;
import com.fl.web.entity.system.TOrg;
import com.fl.web.model.base.BaseTree;
import com.fl.web.service.system.IOrgService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrgServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:47
 */
@Service
@Transactional
public class OrgServiceImpl implements IOrgService {
    @Autowired
    private IOrgDao orgDao;

    @Override
    public TOrg getOrgByCode(String code) {
        return orgDao.getOrgByCode(code);
    }

    @Override
    public TOrg getOrgById(String id) {
        return orgDao.getOrgById(id);
    }

    @Override
    public void saveOrg(TOrg org) {
        org.setId(UUIDUtil.get32UUID());
        org.setStatus(StaticParam.ADD);
        org.setCreateBy(SessionUser.getUserName());
        org.setCreateDate(new Date());
        orgDao.saveOrg(org);
    }

    @Override
    public void updateOrg(TOrg org) {
        org.setStatus(StaticParam.MOD);
        org.setUpdateBy(SessionUser.getUserName());
        org.setUpdateDate(new Date());
        orgDao.updateOrg(org);
    }

    @Override
    public List<TOrg> getOrgByPid(String pid) {
        return orgDao.getOrgByPid(pid);
    }

    @Override
    public void deleteOrgById(String id) {
        orgDao.deleteOrgById(id);
    }


    @Override
    public List<BaseTree> findAllOrgList() {
        List<TOrg> orgList = orgDao.findAllOrgList();
        List<BaseTree> moduleTree = getOrgTreeNode("0", orgList);
        return moduleTree;
    }

    private List<BaseTree> getOrgTreeNode(String pid, List<TOrg> orgList) {
        List<BaseTree> moduleTree = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orgList)) {
            for (TOrg org : orgList) {
                if (org.getPid().equals(pid)) {
                    BaseTree tree = new BaseTree();
                    tree.setValue(org.getId());
                    tree.setLabel(org.getOrgName());
                    // 获取子栏目
                    List<BaseTree> childList = getOrgTreeNode(org.getId(), orgList);
                    if (CollectionUtils.isNotEmpty(childList)) {
                        tree.setChildren(childList);
                    }
                    moduleTree.add(tree);
                }
            }
        }
        return moduleTree;
    }
}
