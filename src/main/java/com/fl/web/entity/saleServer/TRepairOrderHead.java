package com.fl.web.entity.saleServer;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fl.web.entity.base.BaseEntity;
import com.fl.web.entity.mdm.TAttachInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TDeviceRepair
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-15 08:23
 */
@Getter
@Setter
public class TRepairOrderHead extends BaseEntity {
    @Excel(name = "报修单号", orderNum = "2", width = 16)
    private String orderNo;//单据号
    @Excel(name = "客户编码", orderNum = "3")
    private String kunnr;//客户编码
    @Excel(name = "客户名称", orderNum = "4", width = 20)
    private String kunnrName;//客户名称
    @Excel(name = "客户地址", orderNum = "5", width = 20)
    private String address;//地址
    private String contactInfo;//联系人
    private String contactTel;//联系电话
    @Excel(name = "设备SN号", orderNum = "6", width = 20)
    private String deviceSN;//设备SN号
    @Excel(name = "设备名称", orderNum = "7", width = 15)
    private String deviceName;//设备名称
    @Excel(name = "设备型号", orderNum = "8")
    private String deviceNorms;//设备型号
    @Excel(name = "上报人", orderNum = "9")
    private String reportName;//上报人
    @Excel(name = "上报日期", orderNum = "10", width = 15)
    private String reportDate;//上报日期
    @Excel(name = "联系方式", orderNum = "11", width = 15)
    private String reportTel;//上报人电话
    @Excel(name = "故障现象", orderNum = "12", width = 20)
    private String faultNote;//故障现象
    private String receiveBy;
    @Excel(name = "维修员", orderNum = "13")
    private String receiveName;
    private String empCode;//工号
    private String orderStatus;//单据状态
    @Excel(name = "单据状态", orderNum = "1", width = 15)
    private String orderStatusStr;//单据状态
    @Excel(name = "备注", orderNum = "14", width = 20)
    private String note;//备注


    private List<MultipartFile> files;

    private List<TRepairOrderItem> itemList;
    private List<TRepairOrderLog> logList;
    private List<TAttachInfo> attachInfoList;//附件列表

}
