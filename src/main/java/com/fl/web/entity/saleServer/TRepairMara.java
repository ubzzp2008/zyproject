package com.fl.web.entity.saleServer;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TRepairMara
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-30 14:56
 */
@Getter
@Setter
public class TRepairMara extends BaseEntity {
    private String orderNo;//单据号
    private Integer itemNo;//行号
    private String itemId;//行id
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
    private String unit;//单位
    private BigDecimal num;//数量
    private String repairBy;//维修员
    private String state;//单据状态
    private String maraFlag;//OLD:退坏流程，NEW:换新流程
    private String expressWay;//快递方式
    private String expressNum;//快递单号
    private String receiver;//收货人
    private String address;//收货地址
    private String sender;//寄件人
    private String sendDate;//邮寄日期
    private String note;//备注说明
    private String receiveMsgUser;//消息接收人

    private String kunnr;
    private String kunnrName;
    private String deviceSN;
    private String deviceName;
    private String deviceNorms;

}
