package com.fl.web.service.mdm;

import com.fl.web.entity.mdm.TDataDicType;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IDataDicTypeService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-21 15:07
 */
public interface IDataDicTypeService {

    /**
     * @description：获取所有数据字典类型
     * @author：justin
     * @date：2019-11-21 15:06
     */
    public List<TDataDicType> getAllDicType();
}
