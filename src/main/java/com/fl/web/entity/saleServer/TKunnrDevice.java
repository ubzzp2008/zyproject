package com.fl.web.entity.saleServer;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TKunnrDevice
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 09:36
 */
@Getter
@Setter
public class TKunnrDevice extends BaseEntity {
    private String kunnr;//客户编码
    private String kunnrName;//客户名称
    private String address;//客户地址
    private String contactInfo;//联系人
    private String contactTel;//联系电话
    private String deviceSN;//设备SN号
    private String deviceName;//设备名称
    private String deviceNorms;//设备型号
    private String productionDate;//出厂日期
    private String installDate;//装机日期
    private String installAddr;//装机地址
    private String installType;//装机方式
    private String validityDate;//过保日期
    private String note;//备注
    private String flag;//启用标识：0：启用，1：禁用

}
