package com.fl.web.model.sd;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderReqDetail
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-26 12:40
 */
@Getter
@Setter
public class OrderReq extends PageModel {
    private String companyCode;
    private String projectCode;
    private String matnr;
    private String maktx;
    private String norms;
    private String reqStatus;
    private String reqFlag;
    private String enquiryBy;//询价人
}
