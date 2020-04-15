package com.fl.web.dao.shop;

import com.fl.web.entity.shop.TCustomer;
import com.fl.web.model.shop.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IOrgDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:34
 */
public interface ICustomerDao {
    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-13 09:42
     */
     void saveCustomer(TCustomer customer);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
     void updateCustomer(TCustomer customer);

    /**
     * @description：删除数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
    @Delete("delete from customer_info  where id=#{id}")
    public void deleteCustomer(@Param("id") String id);

    /**
     * @description：根据编码获取数据
     * @author：justin
     * @date：2019-12-13 09:53
     */
    @Select("select id,username,phone,sex,birthday,add_date addDate from customer_info where phone=#{phone}")
    @ResultType(TCustomer.class)
    public TCustomer checkPhoneExist(@Param("phone") String phone);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:09
     */
    @Select("select id,username,phone,sex,birthday,add_date addDate from customer_info where id=#{id}")
    @ResultType(TCustomer.class)
    public TCustomer getCustomerById(@Param("id") String id);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 09:43
     */
    public List<TCustomer> queryCustomerList(Customer customer);
}
