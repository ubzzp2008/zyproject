package com.fl.web.service.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TDataDictionary;
import com.fl.web.model.mdm.DataDictionary;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：IDataDictionaryService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-17 14:42
 */
public interface IDataDictionaryService {
    /**
     * @desc：根据组编码获取数据字典集合
     * @author：justin
     * @date：2019-01-17 14:49
     */
    public List<TDataDictionary> findDataDictionaryByGroup(String groupCode);

    /**
     * @description：分页查询数据字典信息
     * @author：justin
     * @date：2019-11-21 15:16
     */
    public PageInfo<TDataDictionary> queryDataDicList(DataDictionary info);

    /**
     * @description：根据组编码和编码验证数据是否存在
     * @author：justin
     * @date：2019-11-21 15:20
     */
    public Integer getDataDictionaryByCode(String groupCode, String code);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-11-21 15:24
     */
    public TDataDictionary getDataDictionaryById(String id);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-11-21 15:26
     */
    public void saveDataDictionary(TDataDictionary info);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-11-21 15:29
     */
    public void updateDataDictionary(TDataDictionary info);

    /**
     * @description：删除数据
     * @author：justin
     * @date：2019-11-21 15:30
     */
    public void deleteDataDictionaryById(String id);
}
