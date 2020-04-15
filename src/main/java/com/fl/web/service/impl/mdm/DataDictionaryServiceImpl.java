package com.fl.web.service.impl.mdm;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.fl.web.dao.mdm.IDataDictionaryDao;
import com.fl.web.entity.mdm.TDataDictionary;
import com.fl.web.model.mdm.DataDictionary;
import com.fl.web.service.mdm.IDataDictionaryService;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：DataDictionaryServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-17 14:41
 */
@Service
@Transactional
public class DataDictionaryServiceImpl implements IDataDictionaryService {
    @Autowired
    private IDataDictionaryDao dataDictionaryDao;

    @Override
    public List<TDataDictionary> findDataDictionaryByGroup(String groupCode) {
        return dataDictionaryDao.findDataDictionaryByGroup(groupCode);
    }

    @Override
    public PageInfo<TDataDictionary> queryDataDicList(DataDictionary info) {
        PageHelper.startPage(info.getPageNum(), info.getPageSize());
        List<TDataDictionary> list = dataDictionaryDao.queryDataDicList(info);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    public Integer getDataDictionaryByCode(String groupCode, String code) {
        return dataDictionaryDao.getDataDictionaryByCode(groupCode, code);
    }

    @Override
    public TDataDictionary getDataDictionaryById(String id) {
        return dataDictionaryDao.getDataDictionaryById(id);
    }

    @Override
    public void saveDataDictionary(TDataDictionary info) {
        info.setId(UUIDUtil.get32UUID());
        dataDictionaryDao.saveDataDictionary(info);
    }

    @Override
    public void updateDataDictionary(TDataDictionary info) {
        dataDictionaryDao.updateDataDictionary(info);
    }

    @Override
    public void deleteDataDictionaryById(String id) {
        dataDictionaryDao.deleteDataDictionaryById(id);
    }
}
