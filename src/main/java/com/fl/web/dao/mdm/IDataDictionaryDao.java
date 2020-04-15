package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TDataDictionary;
import com.fl.web.model.mdm.DataDictionary;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：IDataDictionaryDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-17 14:46
 */
public interface IDataDictionaryDao {
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
    public List<TDataDictionary> queryDataDicList(DataDictionary info);

    /**
     * @description：根据组编码和编码验证数据是否存在
     * @author：justin
     * @date：2019-11-21 15:20
     */
    @Select("select count(1) from data_dictionary where group_code=#{groupCode} and code=#{code}")
    @ResultType(Integer.class)
    public Integer getDataDictionaryByCode(@Param("groupCode") String groupCode, @Param("code") String code);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-11-21 15:24
     */
    @Select("select t.id,t.group_code groupCode,d.group_name groupName,t.code,t.name,t.note,t.sortnum " +
            " from data_dictionary t left join data_dic_type d on t.group_code=d.group_code where t.id=#{id}")
    @ResultType(TDataDictionary.class)
    public TDataDictionary getDataDictionaryById(@Param("id") String id);

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
    @Select("delete from data_dictionary where id=#{id}")
    public void deleteDataDictionaryById(@Param("id") String id);
}
