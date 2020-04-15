package com.fl.web.entity.sd;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TOrderItem
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 16:41
 */
@Getter
@Setter
public class TOrderItem extends BaseEntity {
    private String orderNo;//订单号
    private String reqId;//采购申请id
    private String reqFlag;//需求物料标识
    private Integer itemNo;//行号
    private String maktl;//物料大类编码
    private String maktlName;
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
    private String unit;//单位
    private String manufacturer;//制造商
    private String qualityType;//质量类别
    private BigDecimal price;//单价(元)
    private BigDecimal num;//数量
    private BigDecimal deliveryNum;//已收货数
    private BigDecimal usableNum;//剩余收货数
    private BigDecimal money;//金额
    private String deliveryState;//收货状态  -2待收货，-1部分收货，0完全收货，99差异收货
    private String censorFlag;//送检标识：0不送检，1送检

    private String diffNote;//差异收货备注

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

}
