package com.fl.web.service.impl.mdm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.IMaraDao;
import com.fl.web.dao.mdm.IMaraTypeDao;
import com.fl.web.entity.mdm.TMara;
import com.fl.web.model.mdm.Mara;
import com.fl.web.service.mdm.IMaraService;
import com.fl.web.utils.SessionUser;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：MaraServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 12:46
 */
@Service
@Transactional
public class MaraServiceImpl implements IMaraService {
    @Autowired
    private IMaraDao maraDao;
    @Autowired
    private IMaraTypeDao maraTypeDao;

    @Override
    public PageInfo<TMara> queryMaraList(Mara mara) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(mara.getPageNum(), mara.getPageSize());
        List<TMara> maraList = maraDao.queryMaraList(mara);
        PageInfo result = new PageInfo(maraList);
        return result;
    }

    @Override
    public synchronized void saveMara(TMara mara) {
        mara.setId(UUIDUtil.get32UUID());
        mara.setCreateBy(SessionUser.getUserName());
        mara.setCreateDate(new Date());
        mara.setStatus(StaticParam.ADD);
        mara.setPrice(new BigDecimal(0));
        maraDao.saveMara(mara);
        //更新物料大类中的流水号
        Map<String, Object> map = new HashMap<>();
        Integer sn = Integer.valueOf(mara.getMatnr().substring(4, 10)) + 1;
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        map.put("serialNo", String.format("%06d", sn));
        map.put("maktl", mara.getMaktl());
        maraTypeDao.updateMaraTypeSerialNo(String.format("%06d", sn), mara.getMaktl());
    }

    @Override
    public synchronized String autoSaveMara(TMara mara) {
        //根据物料大类获取物料编码
        String matnr = maraTypeDao.getMatnrByMaktl(mara.getMaktl());
        //获取成功后立即更新物料大类中的流水号
        Integer sn = Integer.valueOf(matnr.substring(4, 10)) + 1;
        // 0 代表前面补充0；6 代表长度为6；d 代表参数为正数型
        maraTypeDao.updateMaraTypeSerialNo(String.format("%06d", sn), mara.getMaktl());
        mara.setId(UUIDUtil.get32UUID());
        mara.setMatnr(matnr);
        mara.setCreateBy(SessionUser.getUserName());
        mara.setCreateDate(new Date());
        mara.setStatus(StaticParam.ADD);
        mara.setPrice(new BigDecimal(0));
        maraDao.saveMara(mara);
        return matnr;
    }

    @Override
    public TMara getMaraByMatnr(String matnr) {
        return maraDao.getMaraByMatnr(matnr);
    }

    @Override
    public TMara getMaraById(String id) {
        return maraDao.getMaraById(id);
    }

    @Override
    public void deleteMaraById(String id) {
        TMara mara = new TMara();
        mara.setId(id);
        mara.setUpdateBy(SessionUser.getUserName());
        mara.setUpdateDate(new Date());
        mara.setStatus(StaticParam.DEL);
        maraDao.updateMara(mara);
    }

    @Override
    public void updateMara(TMara mara) {
        mara.setUpdateBy(SessionUser.getUserName());
        mara.setUpdateDate(new Date());
        mara.setStatus(StaticParam.MOD);
        maraDao.updateMara(mara);
    }

    @Override
    public List<TMara> searchMaraList(String info) {
        return maraDao.searchMaraList(info);
    }

    @Override
    public void saveMaraBatch(List<TMara> maraList) {
        maraDao.saveMaraBatch(maraList);
    }

    @Override
    public List<TMara> exportMaraList(Mara mara) {
        return maraDao.queryMaraList(mara);
    }

    @Override
    public PageInfo<TMara> searchKdMaraList(Mara mara) {
        PageHelper.startPage(mara.getPageNum(), mara.getPageSize());
        List<TMara> maraList = maraDao.searchKdMaraList(mara);
        PageInfo result = new PageInfo(maraList);
        return result;
    }

    @Override
    public void updateMaraPrice(String matnr, BigDecimal price) {
        maraDao.updateMaraPrice(matnr, price);
    }
}
