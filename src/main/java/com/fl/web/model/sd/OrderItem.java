package com.fl.web.model.sd;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderItemDetail
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-30 11:46
 */
@Getter
@Setter
public class OrderItem extends PageModel {
    private String orderNo;
    private String orderStatus;
    private String supplierCode;
    private String supplierName;
    private String companyCode;
    private String projectCode;

}
