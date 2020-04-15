package com.fl.web.service.impl.system;

import com.fl.web.dao.system.IMenuDao;
import com.fl.web.dao.system.IRoleMenuDao;
import com.fl.web.entity.system.TMenu;
import com.fl.web.model.base.ModuleTree;
import com.fl.web.service.system.IMenuService;
import com.fl.web.utils.DateUtils;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：MenuServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 11:03
 */
@Service
@Transactional
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private IMenuDao menuDao;
    @Autowired
    private IRoleMenuDao roleMenuDao;

    @Override
    public List<ModuleTree> findMenuByUserId(String userId, String posId) {
        List<TMenu> moduleList = menuDao.findMenuByUserId(userId, posId, DateUtils.formatDate());
        List<ModuleTree> moduleTree = getModuleTreeNode("0", moduleList);
        return moduleTree;
    }

    @Override
    public List<TMenu> queryMenuList() {
        return menuDao.queryMenuList();
    }

    @Override
    public TMenu getMenuById(String id) {
        return menuDao.getMenuById(id);
    }

    @Override
    public void saveMenu(TMenu menu) {
        menu.setId(UUIDUtil.get32UUID());
        menu.setCreateBy(SessionUser.getUserName());
        menu.setCreateDate(new Date());
        menu.setStatus(StaticParam.ADD);
        menuDao.saveMenu(menu);
    }

    @Override
    public void updateMenu(TMenu menu) {
        menu.setUpdateBy(SessionUser.getUserName());
        menu.setUpdateDate(new Date());
        menu.setStatus(StaticParam.MOD);
        menuDao.updateMenu(menu);
    }

    @Override
    public void deleteMenuById(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", StaticParam.DEL);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        menuDao.deleteMenuById(map);
        // 删除角色与菜单关系表数据
        roleMenuDao.deleteByMenuId(id);

    }

    @Override
    public List<TMenu> getMenuByPid(String pid) {
        return menuDao.getMenuByPid(pid);
    }

    @Override
    public void lockOrUnlockMenu(TMenu menu) {
        menu.setUpdateBy(SessionUser.getUserName());
        menu.setUpdateDate(new Date());
        menu.setStatus(StaticParam.MOD);
        menuDao.lockOrUnlockMenu(menu);
    }

    @Override
    public List<TMenu> getMenuByRoleId(String roleId) {
        return menuDao.getMenuByRoleId(roleId);
    }

    private List<ModuleTree> getModuleTreeNode(String pid, List<TMenu> menuList) {
        List<ModuleTree> moduleTree = new ArrayList<ModuleTree>();
        if (!CollectionUtils.isEmpty(menuList)) {
            for (TMenu menu : menuList) {
                if (menu.getPid().equals(pid)) {
                    ModuleTree tree = new ModuleTree();
                    tree.setId(menu.getId());
                    tree.setName(menu.getName());
                    tree.setDescription(menu.getDescription());
                    tree.setPid(menu.getPid());
                    tree.setIconCls(menu.getIcon());
                    tree.setUrl(menu.getUrl());
                    // 获取子栏目
                    List<ModuleTree> childList = getModuleTreeNode(menu.getId(), menuList);
                    if (CollectionUtils.isNotEmpty(childList)) {
                        tree.setChildrenList(childList);
                    }
                    moduleTree.add(tree);
                }
            }
        }
        return moduleTree;
    }
}
