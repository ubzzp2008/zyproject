package com.fl.web.service.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TSupplierInfo;
import com.fl.web.model.mdm.SupplierInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：ISupplierInfoService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 11:51
 */
public interface ISupplierInfoService {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 11:18
     */
    public PageInfo<TSupplierInfo> querySupplierInfoList(SupplierInfo info);

    /**
     * @desc：保存
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveSupplierInfo(TSupplierInfo info);

    /**
     * @desc：根据编码获取信息
     * @author：justin
     * @date：2019-01-10 12:57
     */
    public TSupplierInfo getSupplierInfoByCode(String code);

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TSupplierInfo getSupplierInfoById(String id);

    /**
     * @description：根据id删除
     * @author：justin
     * @date：2019-10-10 16:23
     */
    public void deleteSupplierInfoById(String id);

    /**
     * @description：修改
     * @author：justin
     * @date：2019-10-10 16:24
     */
    public void updateSupplierInfo(TSupplierInfo info);

    /**
     * @description：前端自动匹配带入查询数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    public List<TSupplierInfo> searchSupplierInfoList(String info);
}
