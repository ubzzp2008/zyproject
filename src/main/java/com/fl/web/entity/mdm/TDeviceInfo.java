package com.fl.web.entity.mdm;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TDeviceInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 15:58
 */
@Getter
@Setter
public class TDeviceInfo extends BaseEntity {
    private String deviceSN;//设备SN号
    private String deviceName;//设备名称
    private String deviceNorms;//设备型号

}
