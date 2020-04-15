package com.fl.web.entity.qm;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

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
public class TMaraCensor extends BaseEntity {
    private String orderNo;
    private String companyCode;
    private String company;
    private String supplierCode;//供应商编码
    private String supplierName;//供应商名称
    private String orderDate;//订单日期
    private String projectCode;//项目类别
    private String projectName;//项目类别
    private String reqDept;//申请部门
    private String reqName;//申请人
    private String purchaser;//采购人
    private String reqId;
    private String itemId;
    private Integer itemNo;
    private String maktl;
    private String maktlName;
    private String matnr;
    private String maktx;
    private String norms;
    private String unit;
    private String manufacturer;//制造商
    private String qualityType;//质量类别
    private BigDecimal num;
    private String state;
    private String receiveUser;
    private Date receiveDate;
    private BigDecimal unqualifiedNum;
    private BigDecimal qualifiedNum;
    private String generationDate;
    private String validityDate;
    private String checkUser;
    private Date checkDate;
    private String reason;
    private String deliveryId;

}
