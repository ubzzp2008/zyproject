package com.fl.web.service.impl.mdm;

import com.fl.web.dao.mdm.IDataDicTypeDao;
import com.fl.web.entity.mdm.TDataDicType;
import com.fl.web.service.mdm.IDataDicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DataDicTypeServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-21 15:07
 */
@Service
@Transactional
public class DataDicTypeServiceImpl implements IDataDicTypeService {
    @Autowired
    private IDataDicTypeDao dataDicTypeDao;

    @Override
    public List<TDataDicType> getAllDicType() {
        return dataDicTypeDao.getAllDicType();
    }
}
