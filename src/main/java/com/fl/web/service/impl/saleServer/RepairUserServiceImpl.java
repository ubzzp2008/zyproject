package com.fl.web.service.impl.saleServer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.saleServer.IRepairUserDao;
import com.fl.web.entity.saleServer.TRepairUser;
import com.fl.web.model.saleServer.RepairUser;
import com.fl.web.service.saleServer.IRepairUserService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RepairUserServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 16:12
 */
@Service
@Transactional
public class RepairUserServiceImpl implements IRepairUserService {
    @Autowired
    private IRepairUserDao repairUserDao;

    @Override
    public PageInfo<TRepairUser> queryRepairUserList(RepairUser info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TRepairUser> list = repairUserDao.queryRepairUserList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public void saveRepairUser(TRepairUser info) {
        info.setId(UUIDUtil.get32UUID());
        info.setStatus(StaticParam.ADD);
        info.setCreateBy(SessionUser.getUserName());
        info.setCreateDate(new Date());
        repairUserDao.saveRepairUser(info);
    }

    @Override
    public void deleteRepairUserById(List<String> idList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("idList", idList);
        map.put("status", StaticParam.DEL);
        map.put("updateBy", SessionUser.getUserName());
        map.put("updateDate", new Date());
        repairUserDao.deleteRepairUser(map);
    }

    @Override
    public TRepairUser findRepairUserByUserName(String userName) {
        return repairUserDao.findRepairUserByUserName(userName);
    }

    @Override
    public List<TRepairUser> findAllRepairUserList() {
        return repairUserDao.queryRepairUserList(null);
    }
}
