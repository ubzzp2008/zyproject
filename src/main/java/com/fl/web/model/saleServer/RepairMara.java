package com.fl.web.model.saleServer;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：RepairMara
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-30 14:58
 */
@Getter
@Setter
public class RepairMara extends PageModel {
    private String orderNo;
    private String state;//单据状态
    private String maraFlag;//0:退坏流程，1:换新流程
    private String repairBy;//维修员
    private String kunnr;
    private String kunnrName;
    private String deviceSN;
    private String deviceName;

}
