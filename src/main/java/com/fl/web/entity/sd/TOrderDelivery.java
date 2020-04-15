package com.fl.web.entity.sd;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TOrderDelivery
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-30 10:13
 */
@Getter
@Setter
public class TOrderDelivery extends BaseEntity {
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
    private String itemId;//采购订单行id
    private Integer itemNo;//行号
    private String maktl;//物料大类编码
    private String maktlName;
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
    private String unit;//单位
    private String manufacturer;//制造商
    private String qualityType;//质量类别
    private BigDecimal orderNum;//订单数量
    private BigDecimal num;//收货数
    private BigDecimal storeNum;//待入库数
    private String reqId;//采购申请表id
    private String state;//数据状态
    private String deliveryFlag;//收货标识：0正常收货，99差异收货
    private String censorFlag;//送检标识：0不送检，1送检
    private String note;//收货差异备注
    private String backBy;//退回人
    private Date backDate;//退回时间
    private String backReason;//退回原因

    private String deliveryState;//收货状态  -2待收货，-1部分收货，0完全收货，99差异收货
}
