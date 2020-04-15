package com.fl.web.service.impl.mdm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.IMaraTypeDao;
import com.fl.web.entity.mdm.TMaraType;
import com.fl.web.model.mdm.MaraType;
import com.fl.web.service.mdm.IMaraTypeService;
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
 * @类名称：MaraTypeServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-10 19:46
 */
@Service
@Transactional
public class MaraTypeServiceImpl implements IMaraTypeService {
    @Autowired
    private IMaraTypeDao maraTypeDao;

    @Override
    public PageInfo<TMaraType> queryMaraTypeList(MaraType maraType) {
        PageHelper.startPage(maraType.getPageNum(), maraType.getPageSize());
        List<TMaraType> maraList = maraTypeDao.queryMaraTypeList(maraType);
        PageInfo result = new PageInfo(maraList);
        return result;
    }

    @Override
    public TMaraType getMaraTypeByMaktl(String maktl) {
        return maraTypeDao.getMaraTypeByMaktl(maktl);
    }

    @Override
    public TMaraType getMaraTypeById(String id) {
        return maraTypeDao.getMaraTypeById(id);
    }

    @Override
    public void saveMaraType(TMaraType maraType) {
        maraType.setId(UUIDUtil.get32UUID());
        maraType.setSerialNo("000001");
        maraType.setCreateBy(SessionUser.getUserName());
        maraType.setCreateDate(new Date());
        maraType.setStatus(StaticParam.ADD);
        maraTypeDao.saveMaraType(maraType);
    }

    @Override
    public void updateMaraType(TMaraType maraType) {
        maraType.setUpdateBy(SessionUser.getUserName());
        maraType.setUpdateDate(new Date());
        maraType.setStatus(StaticParam.MOD);
        maraTypeDao.updateMaraType(maraType);
    }

    @Override
    public void deleteMaraTypeById(String id) {
        TMaraType maraType = new TMaraType();
        maraType.setId(id);
        maraType.setUpdateBy(SessionUser.getUserName());
        maraType.setUpdateDate(new Date());
        maraType.setStatus(StaticParam.DEL);
        maraTypeDao.updateMaraType(maraType);
    }

    @Override
    public List<TMaraType> getAllMaraType() {
        return maraTypeDao.queryMaraTypeList(new MaraType());
    }
}
