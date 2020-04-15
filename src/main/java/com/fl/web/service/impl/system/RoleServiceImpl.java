package com.fl.web.service.impl.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.system.IPositionRoleDao;
import com.fl.web.dao.system.IRoleDao;
import com.fl.web.dao.system.IRoleMenuDao;
import com.fl.web.entity.system.TRole;
import com.fl.web.entity.system.TRoleMenu;
import com.fl.web.model.system.Role;
import com.fl.web.service.system.IRoleService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RoleServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-12 15:04
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private IRoleMenuDao roleMenuDao;
    @Autowired
    private IPositionRoleDao positionRoleDao;

    @Override
    public PageInfo<TRole> queryRoleList(Role role) {
        PageHelper.startPage(role.getPageNum(), role.getPageSize());
        List<TRole> list = roleDao.queryRoleList(role);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public List<TRole> getRolesByPosId(String userId) {
        return roleDao.getRolesByPosId(userId);
    }

    @Override
    public void saveRole(TRole role) {
        role.setId(UUIDUtil.get32UUID());
        role.setCreateBy(SessionUser.getUserName());
        role.setCreateDate(new Date());
        role.setStatus(StaticParam.ADD);
        roleDao.saveRole(role);
    }

    @Override
    public TRole getRoleByCode(String code) {
        return roleDao.getRoleByCode(code);
    }

    @Override
    public TRole getRoleById(String id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public void deleteRoleById(String id) {
        TRole role = new TRole();
        role.setId(id);
        role.setUpdateBy(SessionUser.getUserName());
        role.setUpdateDate(new Date());
        role.setStatus(StaticParam.DEL);
        roleDao.updateRole(role);
        //删除角色与菜单的关系
        roleMenuDao.deleteByRoleId(id);
        //删除岗位与角色的关系
        positionRoleDao.deleteByRoleId(id);
    }

    @Override
    public void updateRole(TRole role) {
        role.setUpdateBy(SessionUser.getUserName());
        role.setUpdateDate(new Date());
        role.setStatus(StaticParam.MOD);
        roleDao.updateRole(role);
    }

    @Override
    public void saveRoleMenus(List<TRoleMenu> list) {
        String roleId = list.get(0).getRoleId();//获取第一个对象的roleId
        //先根据角色删除
        roleMenuDao.deleteByRoleId(roleId);
        //批量新增
        roleMenuDao.saveRoleMenuBatch(list);
    }
}
