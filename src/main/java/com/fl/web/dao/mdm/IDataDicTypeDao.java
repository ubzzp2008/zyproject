package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TDataDicType;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IDataDicTypeDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-21 15:03
 */
public interface IDataDicTypeDao {

    /**
     * @description：获取所有数据字典类型
     * @author：justin
     * @date：2019-11-21 15:06
     */
    public List<TDataDicType> getAllDicType();

}
