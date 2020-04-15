package com.fl.web.model.sd;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderDelivery
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-30 11:14
 */
@Getter
@Setter
public class OrderDelivery extends PageModel {
    private String orderNo;
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
    private String deliveryFlag;//收货类型
    private String censorFlag;//是否送检：0不送检1送检
    private String state;
    private String companyCode;
    private String projectCode;
}
