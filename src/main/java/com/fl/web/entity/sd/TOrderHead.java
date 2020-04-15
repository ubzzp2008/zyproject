package com.fl.web.entity.sd;

import com.fl.web.entity.base.BaseEntity;
import com.fl.web.entity.log.TProcessLog;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TOrderHead
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 16:40
 */
@Getter
@Setter
public class TOrderHead extends BaseEntity {
    private String orderNo;//订单号
    private String companyCode;
    private String company;
    private String supplierCode;//供应商编码
    private String supplierName;//供应商名称
    private String orderDate;//订单日期
    private String projectCode;//项目类别
    private String projectName;//项目类别
    private String arrivalDate;//预计到货日期
    private String reqDept;//申请部门
    private String reqName;//申请人
    private String purchaser;//采购人
    private String payType;//付款方式
    private BigDecimal totalMoney;//应付金额
    private BigDecimal actualMoney;//实付金额
    private BigDecimal restMoney;//待付金额
    private String orderStatus;//订单状态
    private String orderStatusStr;//订单状态
    private String note;//备注
    private List<TOrderItem> itemList;
    private List<TProcessLog> logList;

}
