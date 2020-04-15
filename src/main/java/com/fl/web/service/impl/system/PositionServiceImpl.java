package com.fl.web.service.impl.system;

import com.fl.web.dao.system.IPositionDao;
import com.fl.web.dao.system.IPositionRoleDao;
import com.fl.web.entity.system.TPosition;
import com.fl.web.model.base.BaseTree;
import com.fl.web.service.system.IPositionService;
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
 * @类名称：PositionServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 17:11
 */
@Service
@Transactional
public class PositionServiceImpl implements IPositionService {
    @Autowired
    private IPositionDao positionDao;
    @Autowired
    private IPositionRoleDao positionRoleDao;

    @Override
    public List<TPosition> getPositionByOrgId(String orgId) {
        return positionDao.getPositionByOrgId(orgId);
    }

    @Override
    public TPosition getPositionByCode(String posCode) {
        return positionDao.getPositionByCode(posCode);
    }

    @Override
    public TPosition getPositionById(String id) {
        return positionDao.getPositionById(id);
    }

    @Override
    public void savePosition(TPosition position) {
        position.setId(UUIDUtil.get32UUID());
        position.setCreateBy(SessionUser.getUserName());
        position.setCreateDate(new Date());
        position.setStatus(StaticParam.ADD);
        positionDao.savePosition(position);
    }

    @Override
    public void updatePosition(TPosition position) {
        position.setUpdateBy(SessionUser.getUserName());
        position.setUpdateDate(new Date());
        position.setStatus(StaticParam.MOD);
        positionDao.updatePosition(position);
    }

    @Override
    public void deletePositionById(String id) {
        TPosition position = new TPosition();
        position.setId(id);
        position.setUpdateBy(SessionUser.getUserName());
        position.setUpdateDate(new Date());
        position.setStatus(StaticParam.DEL);
        positionDao.updatePosition(position);
        //需要删除岗位与角色的关系，人员与岗位的关系，在删除岗位时，必须解除该岗位上的人员
        //根据岗位id删除岗位与角色的关系
        positionRoleDao.deleteByPosId(id);
    }

    @Override
    public List<BaseTree> findAllPositionList() {
        List<TPosition> posList = positionDao.findAllPositionList();
        List<BaseTree> moduleTree = getPositionTreeNode("0", posList);
        return moduleTree;
    }

    private List<BaseTree> getPositionTreeNode(String pid, List<TPosition> posList) {
        List<BaseTree> moduleTree = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(posList)) {
            for (TPosition pos : posList) {
                if (pos.getPid().equals(pid)) {
                    BaseTree tree = new BaseTree();
                    tree.setValue(pos.getId());
                    tree.setLabel(pos.getPosName());
                    // 获取子栏目
                    List<BaseTree> childList = getPositionTreeNode(pos.getId(), posList);
                    if (CollectionUtils.isNotEmpty(childList)) {
                        tree.setChildren(childList);
                    }
                    moduleTree.add(tree);
                }
            }
        }
        return moduleTree;
    }

    @Override
    public List<TPosition> getPositionByPid(String pid) {
        return positionDao.getPositionByPid(pid);
    }

}
