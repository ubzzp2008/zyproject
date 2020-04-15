package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TBaseSerialNo;
import org.apache.ibatis.annotations.Param;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：IBaseSerialNoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-15 09:02
 */
public interface IBaseSerialNoDao {
    /**
     * @desc：根据类型获取编码
     * @author：justin
     * @date：2019-01-15 09:07
     */
    public TBaseSerialNo findSerialNoByType(String type);

    /**
     * @desc：保存数据
     * @author：justin
     * @date：2019-01-15 09:10
     */
    public void saveSerialNo(TBaseSerialNo baseDto);

    /**
     * @desc：更新数据
     * @author：justin
     * @date：2019-01-15 09:10
     */
    public void updateSerialNo(@Param("code") String code, @Param("id") String id);
}
