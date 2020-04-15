package com.fl.web.model.mdm;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：DeviceInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 15:57
 */
@Getter
@Setter
public class DeviceInfo extends PageModel {
    private String deviceSN;//设备SN号
    private String deviceName;//设备名称
    private String deviceNorms;//设备型号

}
