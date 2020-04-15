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
 * @类名称：TOrderReqDetail
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-26 12:38
 */
@Getter
@Setter
public class TOrderReq extends BaseEntity {
    private String orderNo;//订单号
    private String projectCode;//项目类别
    private String projectName;//项目类别
    private String companyCode;
    private String company;
    private String maktl;//物料大类编码
    private String maktlName;
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
    private String unit;//单位
    private BigDecimal num;//数量
    private String manufacturer;//制造商
    private String qualityType;//质量类别
    private String suggestSupplier;//议供应商名称
    private String wishDate;//预计要货日期
    private String reqDept;//申请部门
    private String reqName;//申请人
    private String reqDate;//申请日期
    private String reqStatus;//申请单状态
    private String reqStatusStr;//申请单状态
    private String reqNote;//备注
    private String reqFlag;//OLD旧物料流程，NEW新物料流程
    private String enquiryBy;//询价人
    private BigDecimal price;
    private BigDecimal money;
    private List<TProcessLog> logList;
}
