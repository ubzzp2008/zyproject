package com.fl.web.model.saleServer;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DeviceRepairHead
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:24
 */
@Getter
@Setter
public class RepairOrderHead extends PageModel {
    private String kdId;//客户设备表主键
    private String orderNo;//单据号
    private String kunnr;//客户编码
    private String kunnrName;//客户名称
    private String deviceSN;//设备SN号
    private String deviceName;//设备名称
    private String deviceNorms;//设备型号
    private String orderStatus;
    private String receiveBy;
    private List<String> userNameList;

}
