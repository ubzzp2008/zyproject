package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TMaraType;
import com.fl.web.model.mdm.MaraType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMaraTypeDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-10 19:47
 */
public interface IMaraTypeDao {
    /**
     * @description：分页查询物料大类
     * @author：justin
     * @date：2019-10-10 19:54
     */
    public List<TMaraType> queryMaraTypeList(MaraType maraType);

    /**
     * @desc：保存物料大类信息
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveMaraType(TMaraType maraType);

    /**
     * @desc：根据物料大类编码获取信息
     * @author：justin
     * @date：2019-01-10 12:59
     */
    public TMaraType getMaraTypeByMaktl(String maktl);

    /**
     * @description：根据id获取物料大类信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TMaraType getMaraTypeById(String id);

    /**
     * @description：修改物料大类信息
     * @author：justin
     * @date：2019-10-10 16:26
     */
    public void updateMaraType(TMaraType mara);

    /**
     * @description：更新流水号
     * @author：justin
     * @date：2019-11-25 15:34
     */
    @Update("update mara_type set serial_no=#{serialNo} where maktl=#{maktl} and status!='003'")
    public void updateMaraTypeSerialNo(@Param("serialNo") String serialNo, @Param("maktl") String maktl);

    /**
     * @description：根据大类编码获取下一个可用的物料编码
     * @author：justin
     * @date：2019-12-25 10:59
     */
    @Select("select concat(maktl,serial_no) from mara_type where maktl=#{maktl} and status!='003'")
    @ResultType(String.class)
    public String getMatnrByMaktl(@Param("maktl") String maktl);
}
