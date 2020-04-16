package com.fl.web.service.shop;

import com.fl.web.entity.shop.TCustomer;
import com.fl.web.model.shop.Customer;
import com.github.pagehelper.PageInfo;

public interface ICustomerService {
    PageInfo<TCustomer> queryCustomerList(Customer customer);

    void saveCustomer(TCustomer customer);

    void updateCustomer(TCustomer customer);

    void deleteCustomer(String id);

    TCustomer getCustomerByPhone(String phone);

    TCustomer getCustomerById(String id);

}
