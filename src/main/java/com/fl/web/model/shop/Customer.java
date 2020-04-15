package com.fl.web.model.shop;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer extends PageModel {
    private String userName;
    private String phone;
}
