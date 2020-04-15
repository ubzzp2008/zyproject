package com.fl.web.model.saleServer;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DeviceRepair
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:23
 */
@Getter
@Setter
public class RepairOrderItem extends PageModel {
    private String kdId;//客户设备表主键
    private String orderNo;//单据号
    private String matnr;
    private String maktx;
    private String deviceSN;

}
