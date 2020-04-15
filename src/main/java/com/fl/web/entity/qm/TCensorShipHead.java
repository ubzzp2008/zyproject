package com.fl.web.entity.qm;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TMatnrCensor
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 16:51
 */
@Getter
@Setter
public class TCensorShipHead extends BaseEntity {
    private String orderNo;//采购订单号
    private String orderDate;//订单日期
    private String censorNo;//送检单号
    private String purchaser;//采购订单制单人
    private String supplierCode;//供应商编码
    private String supplierName;//供应商名称
    private String consignee;//收货人
    private String surveyor;//检验员
    private String acceptor;//验收人
    private String reqDept;//需求部门
    private String reqName;//需求人
    private String state;//0待提交，10已提交
    private String submitBy;//提交人
    private Date submitDate;//提交时间
    private String exportFlag;//导出标识0未导出1已导出
    private List<TCensorShipItem> itemList;

}
