package com.fl.web.model.sd;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderHead
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 16:40
 */
@Getter
@Setter
public class OrderHead extends PageModel {
    private String orderNo;//订单号
    private String orderStatus;
    private String companyCode;
    private String projectCode;//项目类别
    private String supplierCode;//供应商编码
    private String supplierName;//供应商名称
    private String orderDateStart;//订单日期
    private String orderDateEnd;//订单日期

}
