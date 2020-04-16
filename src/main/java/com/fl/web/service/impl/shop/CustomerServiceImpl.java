package com.fl.web.service.impl.shop;

import com.fl.web.dao.shop.ICustomerDao;
import com.fl.web.entity.shop.TCustomer;
import com.fl.web.model.shop.Customer;
import com.fl.web.service.shop.ICustomerService;
import com.fl.web.utils.DateUtils;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerDao customerDao;

    @Override
    public PageInfo<TCustomer> queryCustomerList(Customer customer) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(customer.getPageNum(), customer.getPageSize());
        List<TCustomer> userList = customerDao.queryCustomerList(customer);
        PageInfo result = new PageInfo(userList);
        return result;
    }

    @Override
    public void saveCustomer(TCustomer customer) {
        customer.setId(UUIDUtil.get32UUID());
        customer.setStatus(StaticParam.ADD);
        customer.setCreateDate(new Date());
        customer.setAddDate(DateUtils.formatDate());
        customerDao.saveCustomer(customer);
    }

    @Override
    public void updateCustomer(TCustomer customer) {
        customer.setStatus(StaticParam.MOD);
        customer.setUpdateDate(new Date());
        customerDao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        customerDao.deleteCustomer(id);
    }

    @Override
    public TCustomer getCustomerByPhone(String phone) {
        return customerDao.getCustomerByPhone(phone);
    }

    @Override
    public TCustomer getCustomerById(String id) {
        return customerDao.getCustomerById(id);
    }
}
