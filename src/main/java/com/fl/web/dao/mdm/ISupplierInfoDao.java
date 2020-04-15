package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TSupplierInfo;
import com.fl.web.model.mdm.SupplierInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：ISupplierInfoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 11:50
 */
public interface ISupplierInfoDao {
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 11:21
     */
    public List<TSupplierInfo> querySupplierInfoList(SupplierInfo info);

    /**
     * @desc：保存信息
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveSupplierInfo(TSupplierInfo info);

    /**
     * @desc：根据编码获取信息
     * @author：justin
     * @date：2019-01-10 12:59
     */
    @Select("select id,supplier_code supplierCode,supplier_name supplierName,supplier_type supplierType," +
            " address,contact,contact_tel contactTel,telphone,bank_type bankType,bank_account bankAccount  " +
            " from supplier_info where status!='003' and supplier_code = #{code} ")
    @ResultType(TSupplierInfo.class)
    public TSupplierInfo getSupplierInfoByCode(@Param("code") String code);

    /**
     * @description：根据id获取信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    @Select("select id,supplier_code supplierCode,supplier_name supplierName,supplier_type supplierType," +
            " address,contact,contact_tel contactTel,telphone,bank_type bankType,bank_account bankAccount " +
            " from supplier_info where status!='003' and id=#{id}")
    @ResultType(TSupplierInfo.class)
    public TSupplierInfo getSupplierInfoById(@Param("id") String id);

    /**
     * @description：修改信息
     * @author：justin
     * @date：2019-10-10 16:26
     */
    public void updateSupplierInfo(TSupplierInfo info);

    /**
     * @description：前端自动匹配带入查询数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    @Select("select id,supplier_code supplierCode,supplier_name supplierName,supplier_type supplierType," +
            " address,contact,contact_tel contactTel,telphone,bank_type bankType,bank_account bankAccount " +
            " from supplier_info where status!='003' and (supplier_code like #{info} or supplier_name like #{info}) limit 20")
    @ResultType(TSupplierInfo.class)
    public List<TSupplierInfo> searchSupplierInfoList(String info);
}
