package com.fl.web.model.saleServer;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：KunnrDevice
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 09:43
 */
@Getter
@Setter
public class KunnrDevice extends PageModel {
    private String kunnr;//客户编码
    private String kunnrName;//客户名称
    private String deviceSN;//设备SN号
    private String deviceName;//设备名称
    private String deviceNorms;//设备型号
    private String installType;//装机方式

}
