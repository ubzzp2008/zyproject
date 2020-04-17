package com.fl.web.model.shop;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReport extends PageModel {
    private String goodsCode;
    private String goodsName;
    private String orderDate;
}
