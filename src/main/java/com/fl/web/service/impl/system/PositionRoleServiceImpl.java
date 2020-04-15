package com.fl.web.service.impl.system;

import com.fl.web.dao.system.IPositionRoleDao;
import com.fl.web.entity.system.TPositionRole;
import com.fl.web.service.system.IPositionRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：PositionRoleServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-19 16:31
 */
@Service
@Transactional
public class PositionRoleServiceImpl implements IPositionRoleService {
    @Autowired
    private IPositionRoleDao positionRoleDao;

    @Override
    public void savePositionRoles(List<TPositionRole> list) {
        String posId = list.get(0).getPosId();//获取第一个对象的posId
        //先根据岗位id删除
        positionRoleDao.deleteByPosId(posId);
        positionRoleDao.savePositionRoles(list);
    }
}
