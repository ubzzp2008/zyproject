package com.fl.web.entity.shop;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TCustomer extends BaseEntity {
    private String userName;
    private String phone;
    private String sex;
    private String birthday;
    private String addDate;
}
