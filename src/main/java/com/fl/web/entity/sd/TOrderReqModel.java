package com.fl.web.entity.sd;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TOrderReqModel
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-26 17:17
 */
@Getter
@Setter
public class TOrderReqModel {
    private String projectCode;
    private String companyCode;
    private String company;
    private String reqDept;//申请部门
    private String reqName;//申请人
    private String reqDate;//申请日期
    private String reqFlag;//OLD旧物料流程，NEW新物料流程
    private String reqStatus;//申请单状态
    private List<TOrderReq> itemList;
}
