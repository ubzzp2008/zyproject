package com.fl.web.service.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TMaraType;
import com.fl.web.model.mdm.MaraType;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMaraTypeService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-10 19:47
 */
public interface IMaraTypeService {
    /**
     * @description：分页查询物料大类
     * @author：justin
     * @date：2019-10-10 20:15
     */
    public PageInfo<TMaraType> queryMaraTypeList(MaraType maraType);

    /**
     * @description：根据编码获取数据
     * @author：justin
     * @date：2019-10-10 20:15
     */
    public TMaraType getMaraTypeByMaktl(String maktl);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-10-10 20:15
     */
    public TMaraType getMaraTypeById(String id);

    /**
     * @description：保存物料大类信息
     * @author：justin
     * @date：2019-10-10 20:15
     */
    public void saveMaraType(TMaraType maraType);

    /**
     * @description：修改物料大类
     * @author：justin
     * @date：2019-10-10 20:16
     */
    public void updateMaraType(TMaraType maraType);

    /**
     * @description：根据id删除物料大类
     * @author：justin
     * @date：2019-10-10 20:16
     */
    public void deleteMaraTypeById(String id);

    /**
     * @description：获取所有物料大类
     * @author：justin
     * @date：2019-10-10 20:26
     */
    public List<TMaraType> getAllMaraType();
}
