package com.fl.web.entity.saleServer;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TDeviceRepairItem
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:23
 */
@Getter
@Setter
public class TRepairOrderItem extends BaseEntity {
    private String orderNo;//单据号
    private String kunnr;//客户编码
    private String kunnrName;//客户名称
    private String deviceSN;//设备SN号
    private String deviceName;//设备名称
    private String deviceNorms;//设备型号
    private Integer itemNo;//行号
    private String matnr;
    private String maktx;
    private String norms;
    private String unit;
    private BigDecimal num;
    private String dealType;
    private String dealName;
    private String dealState;
    private String dealStateStr;
    private String note;//备注
    private String repairBy;//维修员
    private String repairName;//维修员

}
